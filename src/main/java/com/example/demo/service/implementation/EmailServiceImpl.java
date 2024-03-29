package com.example.demo.service.implementation;

import com.example.demo.enumeration.VerificationType;
import com.example.demo.exception.ApiException;
import com.example.demo.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String username, String email, String verificationUrl, VerificationType verificationType) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dizvabole@gmail.com");
            message.setTo(email);
            message.setText(getEmailMessage(username, verificationUrl, verificationType));
            message.setSubject(String.format("Harvest Share Management - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mailSender.send(message);
            log.info("Email sent to {}", username);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private String getEmailMessage(String username, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> {
                return "Hello " + username + "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl + "\n\nThe Support Team";
            }
            case ACCOUNT -> {
                return "Hello " + username + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl + "\n\nThe Support Team";
            }
            default -> throw new ApiException("Unable to send email. Unknown email");
        }
    }
}
