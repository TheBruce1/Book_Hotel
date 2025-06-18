package com.sachin.service.interfaces;

import com.sachin.dto.LoginRequest;
import com.sachin.dto.Response;
import com.sachin.entity.User;

public interface IUserService {

	Response register(User user);

	Response login(LoginRequest loginRequest);

	Response getAllUsers();

	Response getUserBookingHistory(String userId);

	Response deleteUser(String userId);

	Response getUserById(String userId);

	Response getMyInfo(String email);
}
