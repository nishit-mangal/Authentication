package com.authentication.authentication.Service;

public interface OTPService {
    public void sendOTP(String Email, Long userId, String phoneNumber);
}
