package com.pk.email.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceInternal {
    private JavaMailSender mailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        log.info("sendSimpleMessage, to: {}, subject: {}", to, subject);
        log.debug("sendSimpleMessage, text: {}", text);
        SimpleMailMessage message = new SimpleMailMessage(); 
        // TODO make it customizable
        message.setFrom("noreply@pkproject.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlMsg) throws MessagingException {
      log.info("sendHtmlMessage, to: {}, subject: {}", to, subject);
      log.debug("sendSimpleMessage, text: {}", htmlMsg);
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(htmlMsg, true);
      helper.setTo(to);
      helper.setSubject(subject);
      // TODO make it customizable
      helper.setFrom("noreply@pkproject.com");
      mailSender.send(mimeMessage);
    }
}
