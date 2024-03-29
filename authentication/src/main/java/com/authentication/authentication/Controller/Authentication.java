package com.authentication.authentication.Controller;

import com.authentication.authentication.DTO.Requests.UserRegisterRequest;
import com.authentication.authentication.DTO.Response.ResponseData;
import com.authentication.authentication.DTO.Response.UserRegisterResponse;
import com.authentication.authentication.Service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class Authentication {
    @Autowired
    UserRegisterService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUsers(@RequestBody UserRegisterRequest userReq){
        UserRegisterResponse userResp = new UserRegisterResponse();
        ResponseData responseData = new ResponseData();
        if(userReq.getEmail()==null || userReq.getPassword()==null || userReq.getPhoneNumber()==null || userReq.getUserName()==null || userReq.getPhoneNumber().length()!=10){
            responseData.setResponseCode(400);
            responseData.setResponseMessage("Missing Parameters");
            userResp.setResponseData(responseData);
            return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
        }
        userResp = userService.registerUser(userReq);
        if(userResp==null || userResp.getUserName()==null || userResp.getEmail()==null){
            responseData.setResponseCode(400);
            responseData.setResponseMessage("User already exists");
            userResp.setResponseData(responseData);
            return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
        }
        responseData.setResponseCode(HttpStatusCode.valueOf(200).value());
        responseData.setResponseMessage("User Registered Successfully");
        userResp.setResponseData(responseData);
        return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity <UserRegisterResponse> loginUser(@RequestBody UserRegisterRequest userReq){
        UserRegisterResponse userResp = new UserRegisterResponse();
        //validation
        if(userReq.getEmail()==null || userReq.getPassword()==null){
            ResponseData responseData = new ResponseData();
            responseData.setResponseCode(400);
            responseData.setResponseMessage("Missing Parameters");
            userResp.setResponseData(responseData);
        }else{
            userResp = userService.validateLogin(userReq);
        }
        return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/validatePhoneAndOTP")
    public ResponseEntity <UserRegisterResponse> validateOTP(@RequestBody UserRegisterRequest userReq){
        UserRegisterResponse userResp = new UserRegisterResponse();
        //input validation
        if(userReq.getOtp()==null || userReq.getPhoneNumber()==null){
            ResponseData responseData = new ResponseData();
            responseData.setResponseCode(400);
            responseData.setResponseMessage("Invalid Input");
            userResp.setResponseData(responseData);
            return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
        }
        userResp = userService.validateOTP(userReq);
        return new ResponseEntity<UserRegisterResponse>(userResp, HttpStatusCode.valueOf(200));
    }
}
