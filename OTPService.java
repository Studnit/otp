package com.auruspay.otp;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OTPService {

    // Map to store OTP and expiration time (key: email/phone, value: OTP and expiry)
    private static final Map<String, OTPDetails> otpStore = new HashMap<>();

    // SecureRandom for OTP generation
    private static final SecureRandom secureRandom = new SecureRandom();

    // OTP generation logic
    public static String generateOTP(String identifier) {
        // Generate a 6-digit OTP
        int otp = 100000 + secureRandom.nextInt(900000); // Random number between 100000-999999

        // Set OTP expiry (e.g., 5 minutes from now)
        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000);

        // Store the OTP and its expiry
        otpStore.put(identifier, new OTPDetails(String.valueOf(otp), expiryTime));

        // Simulate sending the OTP (email/SMS)
        System.out.println("OTP for " + identifier + ": " + otp);

        return String.valueOf(otp);
    }

    // Validate OTP
    public static boolean validateOTP(String identifier, String otp) {
        OTPDetails otpDetails = otpStore.get(identifier);

        if (otpDetails == null) {
            System.out.println("OTP not found for identifier: " + identifier);
            return false;
        }

        // Check if OTP is expired
        if (otpDetails.getExpiryTime() < System.currentTimeMillis()) {
            System.out.println("OTP expired for identifier: " + identifier);
            otpStore.remove(identifier); // Remove expired OTP
            return false;
        }

        // Check if OTP matches
        if (otpDetails.getOtp().equals(otp)) {
            System.out.println("OTP verified successfully for identifier: " + identifier);
            otpStore.remove(identifier); // Remove OTP after successful verification
            return true;
        } else {
            System.out.println("Invalid OTP for identifier: " + identifier);
            return false;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        String identifier = "nkharose@aurusinc.com"; // Replace with email/phone
        boolean isValid = false ;
        do {
        String generatedOTP = generateOTP(identifier);
        EmailService.main(identifier, generatedOTP);
        Scanner sc = new Scanner(System.in);
       
        // Simulate user entering OTP
        String userInput = "123456"; // Replace with user input
        System.out.print("Enter your OTP :"  );
        userInput= String.valueOf(sc.nextInt());
       System.out.println();
         isValid = validateOTP(identifier, userInput);

        System.out.println("Is OTP valid? " + isValid);
        
    }while(!isValid);
    }
}

// Helper class to store OTP details
class OTPDetails {
    private final String otp;
    private final long expiryTime;

    public OTPDetails(String otp, long expiryTime) {
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

    public String getOtp() {
        return otp;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
