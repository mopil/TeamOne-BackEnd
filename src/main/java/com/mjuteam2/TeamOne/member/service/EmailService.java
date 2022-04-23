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
import java.util.Random;

import static com.mjuteam2.TeamOne.member.config.TempPassword.tempPassword;

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

    public String sendMail(String email) {
        String temp = tempPassword(6,true);
        try {
            emailSender.send(createResetPasswordMessage(email, temp));
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new SignUpException("인증 메일 전송 오류");
        }
        return temp;
    }


    private MimeMessage createResetPasswordMessage(String email, String memberKey) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("TeamOne 임시 비밀번호 입니다.");
        String text = "";
        text += "<div style='margin:100px;'>";
        text += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        text += "<h3 style='color:blue;'>임시비밀번호 발급</h3>";
        text += "<div style='font-size:130%'>";
        text += "<br/>임시비밀번호 :   <h2>" + memberKey + "</h2>";
        text += "<br/>로그인 후 비밀번호 변경을 해주세요.";
        text += "</div>";
        message.setText(text, "utf-8", "html");
        message.setFrom(new InternetAddress("teamoneauth@gmail.com", "TeamOne"));
        emailSender.send(message);
        return message;
    }
}
