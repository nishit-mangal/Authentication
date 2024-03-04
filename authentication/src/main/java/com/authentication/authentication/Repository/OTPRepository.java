package com.authentication.authentication.Repository;

import com.authentication.authentication.Models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByOtpAndIsValidTrue(Long otp);
    OTP findByOtpAndUserIdAndIsValidTrue(Long otp, Long userId);
}
