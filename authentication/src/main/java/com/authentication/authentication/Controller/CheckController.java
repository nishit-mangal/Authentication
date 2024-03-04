package com.authentication.authentication.Controller;

import com.authentication.authentication.DTO.Response.ResponseData;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
    @GetMapping("/healthCheck")
    public ResponseEntity<ResponseData> checkAuthentication(){
        ResponseData responseData = new ResponseData();
        responseData.setResponseCode(200);
        responseData.setResponseMessage("Working...");
        return new ResponseEntity<ResponseData>(responseData, HttpStatusCode.valueOf(200));
    }
}
