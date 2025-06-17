package com.restaurant.Service;

import com.restaurant.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // ✅ Send Verification Token Email
    public void sendVerificationToken(User user, String token) {
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        String subject = "Email Verification";
        String body = "Dear " + user.getUsername() + ",\n\n"
                + "Thank you for registering. Please click the link below to verify your email:\n"
                + verificationUrl + "\n\n"
                + "If you did not register, please ignore this email.\n\n"
                + "Best regards,\nRestaurant Management Team";

        sendEmail(user.getEmail(), subject, body);
    }
}
