package com.sachin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.dto.Response;
import com.sachin.service.interfaces.IUserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllUsers() {
		Response response = userService.getAllUsers();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("get-by-id/{userId}")
	public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId) {
		Response response = userService.getUserById(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
		Response response = userService.deleteUser(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("/get-logged-in-profile-info")
	public ResponseEntity<Response> getLoggedInProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Response response = userService.getMyInfo(email);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("/get-user-bookings/{userId}")
	public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId) {
		Response response = userService.getUserBookingHistory(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
