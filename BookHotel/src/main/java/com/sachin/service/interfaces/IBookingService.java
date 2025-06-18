package com.sachin.service.interfaces;

import com.sachin.dto.Response;
import com.sachin.entity.Booking;

public interface IBookingService {

	Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

	Response findBookingByConfirmationCode(String confirmationCode);

	Response getAllBookings();

	Response cancelBooking(Long bookingId);

}
