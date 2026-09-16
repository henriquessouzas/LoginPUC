package com.example.LoginPUC.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private final JavaMailSender mailSender;

    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("noreply@loginpuc.com");
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Falha ao enviar e-mail: " + e.getMessage());
        }
    }

    public void sendRecoveryEmail(String to, String token) {
        String link = "http://localhost:8080/resetSenha?token=" + token;
        sendEmail(to, "Recuperação de Senha",
                "Clique no link abaixo para redefinir sua senha:\n\n" + link + "\n\nO link expira em 30 minutos.");
    }
}
