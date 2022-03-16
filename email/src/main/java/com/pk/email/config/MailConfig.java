package com.pk.email.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailConfig {

  @Value("${shinano_email_host}")
  private String emailHost;
  
  @Value("${shinano_email_port}")
  private String emailPort;
  
  @Value("${shinano_email_username}")
  private String emailUsername;
  
  @Value("${shinano_email_password}")
  private String emailPassword;

  @Bean
  public JavaMailSender getJavaMailSender() {
    log.info("Host: {}", emailHost);
    log.info("Port: {}", emailPort);
    log.info("Username: {}", emailUsername);
    log.info("Password length: {}", emailPassword.length());

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(emailHost);
    mailSender.setPort(Integer.parseInt(emailPort));

    mailSender.setUsername(emailUsername);
    mailSender.setPassword(emailPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "false");
    return mailSender;
  }
}
