package com.authentication.authentication.Service;

import com.authentication.authentication.DTO.Requests.UserRegisterRequest;
import com.authentication.authentication.DTO.Response.UserRegisterResponse;

public interface UserRegisterService {
    public UserRegisterResponse registerUser(UserRegisterRequest user);
    public UserRegisterResponse validateLogin(UserRegisterRequest user);
    public UserRegisterResponse validateOTP(UserRegisterRequest user);
}

