package com.authentication.authentication.DTO.Requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;
    private Long otp;
    private Long userId;
    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
