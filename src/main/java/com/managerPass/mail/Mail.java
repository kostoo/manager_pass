package com.managerPass.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mail {

    private final JavaMailSender javaMailSender;

     @Value(value = "${app.mail}")
     private String address;

    public void sendEmail(String emailTo,String subject,String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(address);
        msg.setTo(emailTo);
        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }
}
