package com.hh99.nearby.signup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${mail.url}")
    URL url;

    @Value("${mail.image.url}")
    URL url2;

    @Value("${spring.mail.username}")
    String sender;

    public void sendSimpleMessage(String email, Long id) throws MessagingException {
        String Text = "<h1><a href="+ url + id + ">이메일 인증 확인</a></h1>";

        MimeMessage mail = emailSender.createMimeMessage();
        mail.setFrom(sender);
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mail.setSubject("쓱관 가입확인 메일입니다.");
        mail.setText(Text,"UTF-8","html");
        emailSender.send(mail);
    }
}
