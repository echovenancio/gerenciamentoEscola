package com.escola.gerencimentoEscola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired   
    private JavaMailSender mailSender;

    public void enviarEmail(String subject, String text, String sendTo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("secretaria@escolar.com");
        mailSender.send(message);
    }
}
