package com.authentication.authentication.Service.ServiceImpl;

import com.authentication.authentication.Models.OTP;
import com.authentication.authentication.Repository.OTPRepository;
import com.authentication.authentication.Service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OTPRepository otpRepo;

    @Override
    @Async
    public void sendOTP(String email, Long userId, String phoneNumber){
        Long otp = generateOTP();
        OTP existingOtp = otpRepo.findByOtpAndIsValidTrue(otp);

        if(existingOtp!=null){
            existingOtp.setIsValid(false);
            otpRepo.save(existingOtp);
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("mangalnishit07@gmail.com");
        mailMessage.setTo("nishitm18e@iiitt.ac.in");
        mailMessage.setSubject("Your One-Time Password (OTP)");
        mailMessage.setText("Your OTP is: " + otp);

        javaMailSender.send(mailMessage);

        OTP saveOtp = new OTP();
        saveOtp.setIsValid(true);
        saveOtp.setOtp(otp);
        saveOtp.setOtpSentDate(new Date());
        saveOtp.setUserId(userId);
        saveOtp.setPhoneNumber(phoneNumber);
        otpRepo.save(saveOtp);
    }
    private Long generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        Long otpValue = (long) (100000 + random.nextInt(900000));
        return (otpValue);
    }
}
