package edu.esoft.sdp.cw.pickandgoapi.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class OTPGenerator {

    public static synchronized int generateRandomOtp() {

        // Length of OTP

        int length = 6;

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random randomMethod = new Random();

        char[] otp = new char[length];

        for (int i = 0; i < length; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(randomMethod.nextInt(numbers.length()));
        }
        return Integer.parseInt(String.valueOf(otp));
    }
}
