package com.authentication.authentication.Service.ServiceImpl;

import com.authentication.authentication.DTO.Requests.UserRegisterRequest;
import com.authentication.authentication.DTO.Response.ResponseData;
import com.authentication.authentication.DTO.Response.UserRegisterResponse;
import com.authentication.authentication.Models.OTP;
import com.authentication.authentication.Models.Users;
import com.authentication.authentication.Repository.OTPRepository;
import com.authentication.authentication.Repository.UsersRepository;
import com.authentication.authentication.Service.OTPService;
import com.authentication.authentication.Service.UserRegisterService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Builder
public class UserRegisterImpl implements UserRegisterService {

    @Autowired
    UsersRepository userRepo;
    @Autowired
    OTPRepository otpRepository;

    @Autowired
    private OTPService otpService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest user) {
        UserRegisterResponse userResp = new UserRegisterResponse();
        try {
            Users userExisting = userRepo.findByEmail(user.getEmail());
            Users newUser;
            if(userExisting!=null)
                throw new Exception("User Already Exists");
            else
                newUser = new Users();

            newUser.setUsername(user.getUserName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setPhoneNumber(user.getPhoneNumber());
            userRepo.save(newUser);
            userResp.setEmail(newUser.getEmail());
            userResp.setUserName(newUser.getUsername());
        } catch (Exception e) {
            e.getMessage();
            return new UserRegisterResponse();
        }
        return userResp;
    }

    @Override
    public UserRegisterResponse validateLogin(UserRegisterRequest user) {
        UserRegisterResponse response = new UserRegisterResponse();
        ResponseData responseData = new ResponseData();
        try{
            Users existingUsers = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if(existingUsers==null){
                throw new Exception("User not found");
            }

            //send OTP
            otpService.sendOTP(existingUsers.getEmail(), existingUsers.getId(), existingUsers.getPhoneNumber());

            responseData.setResponseMessage("OTP sent Successfully");
            responseData.setResponseCode(200);

            response.setEmail(user.getEmail());
            response.setUserName(existingUsers.getUsername());
            response.setUserId(existingUsers.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            responseData.setResponseCode(500);
            responseData.setResponseMessage("Validation Failed");
        }
        response.setResponseData(responseData);
        return response;
    }

    @Override
    public UserRegisterResponse validateOTP(UserRegisterRequest user) {
        UserRegisterResponse response = new UserRegisterResponse();
        ResponseData responseData = new ResponseData();
        try{
            OTP existingOTP = otpRepository.findByOtpAndPhoneNumberAndIsValidTrue(user.getOtp(), user.getPhoneNumber());
            if(existingOTP==null){
                throw new Exception("Wrong OTP");
            }

            existingOTP.setIsValid(false);
            otpRepository.save(existingOTP);

            //OTP is validated now generate Access Token
            String jwtTokenGenerated = jwtUtil.generateToken(user.getPhoneNumber());

            response.setJwtToken(jwtTokenGenerated);

            responseData.setResponseCode(200);
            responseData.setResponseMessage("OTP validated Successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            responseData.setResponseMessage("OTP Verification Failed");
            responseData.setResponseCode(401);
        }
        response.setResponseData(responseData);
        return response;
    }
}
