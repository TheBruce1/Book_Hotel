package com.sachin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.dto.LoginRequest;
import com.sachin.dto.Response;
import com.sachin.entity.User;
import com.sachin.service.interfaces.IUserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody User user) {
		Response response = userService.register(user);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
		Response response = userService.login(loginRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
