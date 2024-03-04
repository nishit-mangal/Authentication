package com.authentication.authentication.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterResponse {
    private String userName;
    private String email;
    private ResponseData responseData;
    private Long userId;
    private String jwtToken;
}
