package com.sachin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sachin.dto.BookingDTO;
import com.sachin.dto.Response;
import com.sachin.entity.Booking;
import com.sachin.entity.Room;
import com.sachin.entity.User;
import com.sachin.exception.OurException;
import com.sachin.repo.BookingRepository;
import com.sachin.repo.RoomRepository;
import com.sachin.repo.UserRepository;
import com.sachin.service.interfaces.IBookingService;
import com.sachin.service.interfaces.IRoomService;
import com.sachin.utils.Utils;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private IRoomService roomService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
		Response response = new Response();

		try {
			if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("Checkin date must come after checkout date!");
			}
			Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found!"));
			User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found!"));

			List<Booking> existingBookings = room.getBookings();
			if (!roomIsAvailable(bookingRequest, existingBookings)) {
				throw new OurException("Room is not available for selected date range...");
			}
			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			bookingRepository.save(bookingRequest);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBookingConfirmationCode(bookingConfirmationCode);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a booking : " + e.getMessage());
		}
		return response;
	}

	private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
	}

	@Override
	public Response findBookingByConfirmationCode(String confirmationCode) {
		Response response = new Response();

		try {
			Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
					.orElseThrow(() -> new OurException("Booking not found!"));
			BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);

			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBookings(bookingDTO);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error getting booking by confirmation code : " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllBookings() {
		Response response = new Response();

		try {
			List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);

			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBookingList(bookingDTOList);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error getting all bookings : " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response cancelBooking(Long bookingId) {
		Response response = new Response();

		try {
			bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking does not exists!"));
			bookingRepository.deleteById(bookingId);

			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error cancelling a booking: " + e.getMessage());
		}
		return response;
	}

}
