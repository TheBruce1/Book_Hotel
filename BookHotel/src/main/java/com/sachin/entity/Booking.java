package com.sachin.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "check in date is required")
	private LocalDate checkInDate;

	@Future(message = "check out date must be in the future")
	private LocalDate checkOutDate;

	@Min(value = 1, message = "Number of adults must not be less than 1")
	private int numberOfAdults;

	@Min(value = 0, message = "Number of children must not be less than 0")
	private int numberOfChildrens;

	private int totalNumOfGuests;

	private String bookingConfirmationCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	
	public void calculateTotalNumberOfGuests() {
		this.totalNumOfGuests = this.numberOfAdults + this.numberOfChildrens;
	}
	
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults=numberOfAdults;
		calculateTotalNumberOfGuests();
	}
	
	public void setNumberOfChildrens(int numberOfChildrens) {
		this.setNumberOfChildrens(numberOfChildrens);
		calculateTotalNumberOfGuests();
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", numberOfAdults=" + numberOfAdults + ", numberOfChildrens=" + numberOfChildrens
				+ ", totalNumOfGuests=" + totalNumOfGuests + ", bookingConfirmationCode=" + bookingConfirmationCode
				+ "]";
	}
	
	
}
