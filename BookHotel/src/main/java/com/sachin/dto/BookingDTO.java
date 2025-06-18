package com.sachin.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

	private Long id;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private int numberOfAdults;
	private int numberOfChildren;
	private int totalNumOfGuests;
	private String bookingConfirmationCode;
	private UserDTO user;
	private RoomDTO room;
}
