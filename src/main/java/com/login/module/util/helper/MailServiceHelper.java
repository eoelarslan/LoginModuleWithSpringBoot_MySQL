package com.login.module.util.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailServiceHelper {


    @Autowired
    MessageHelper messageHelper;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${custom.mail.verification.url}")
    private String mailVerificationUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    public String createEmailToken() {

        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = lowerCase.toUpperCase();
        String numerics = "0123456789";

        String[] mixedArray = {lowerCase, upperCase, numerics};
        String mixed = "";
        String token = "";

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 3; j++)
                mixed += mixedArray[new Random().nextInt(3)];
            token += mixed.charAt(new Random().nextInt(mixed.length()));
        }

        return token;
    }

    public String createRegisterMessage(String code, String receiverMail, String emailToken) {

        receiverMail = Base64Helper.encoder(receiverMail);

        return messageHelper.getMessage(code, null) + "\n\n" + mailVerificationUrl + receiverMail + "/" + emailToken;
    }

    public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(sender);
        javaMailSender.send(mailMessage);
    }
}