package com.mjuteam2.TeamOne.member.service;

import com.mjuteam2.TeamOne.member.exception.SignUpException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    private MimeMessage createMessage(String email, String authToken) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("TeamOne 회원가입 인증 메일이 도착했습니다.");
        String text="";
        text+= "<div style='margin:100px;'>";
        text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        text+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
        text+= "<div style='font-size:130%'>";
        text+= "CODE : <strong>";
        text+= authToken+"</strong><div><br/> ";
        text+= "</div>";
        message.setText(text, "utf-8", "html");
        message.setFrom(new InternetAddress("teamoneauth@gmail.com","TeamOne"));
        return message;
    }

    public void sendMail(String email, String authToken) {
        String totalAddress = email + "@mju.ac.kr";
        try {
            emailSender.send(createMessage(totalAddress, authToken));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SignUpException("인증 메일 전송 오류");
        }
    }
}
