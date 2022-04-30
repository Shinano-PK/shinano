package com.pk.email.service;

import com.google.common.io.CharStreams;
import com.pk.email.exception.EmailException;
import com.pk.email.model.Email;
import com.pk.email.model.EmailDB;
import com.pk.email.repository.EmailRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
  private EmailServiceInternal emailServiceInt;
  private String emailCreateAccTemplate;
  private String emailConfirmedTemplate;
  private String emailResetPassTemplate;
  private EmailRepository emailRepository;

  public EmailService(
      EmailServiceInternal emailServiceInternal,
      ResourceLoader resourceLoader,
      EmailRepository emailRepository)
      throws IOException {
    this.emailRepository = emailRepository;
    this.emailServiceInt = emailServiceInternal;
    emailCreateAccTemplate =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:acc_created.html").getInputStream(),
                StandardCharsets.UTF_8));
    emailConfirmedTemplate =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:acc_confirmed.html").getInputStream(),
                StandardCharsets.UTF_8));
    emailResetPassTemplate =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:password_reset.html").getInputStream(),
                StandardCharsets.UTF_8));

    String logoImgB64 =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:img/logo.b64").getInputStream(),
                StandardCharsets.UTF_8));

    String witamyImgB64 =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:img/WITAMY.b64").getInputStream(),
                StandardCharsets.UTF_8));
    String tloImgB64 =
        CharStreams.toString(
            new InputStreamReader(
                resourceLoader.getResource("classpath:img/TLO.b64").getInputStream(),
                StandardCharsets.UTF_8));

    log.info("emailCreateAcc: {} (first 500 chars)", emailCreateAccTemplate.substring(0, 500));
    log.info("emailConfirmed: {} (first 500 chars)", emailConfirmedTemplate.substring(0, 500));
    log.info("emailResetPass: {} (first 500 chars)", emailResetPassTemplate.substring(0, 500));

    emailCreateAccTemplate = emailCreateAccTemplate.replace("{{LOGO}}", logoImgB64);
    emailCreateAccTemplate = emailCreateAccTemplate.replace("{{TLO}}", tloImgB64);
    emailCreateAccTemplate = emailCreateAccTemplate.replace("{{WITAMY}}", witamyImgB64);

    emailConfirmedTemplate = emailConfirmedTemplate.replace("{{LOGO}}", logoImgB64);
    emailConfirmedTemplate = emailConfirmedTemplate.replace("{{TLO}}", tloImgB64);
    emailConfirmedTemplate = emailConfirmedTemplate.replace("{{WITAMY}}", witamyImgB64);

    emailResetPassTemplate = emailResetPassTemplate.replace("{{LOGO}}", logoImgB64);
    emailResetPassTemplate = emailResetPassTemplate.replace("{{TLO}}", tloImgB64);
    emailResetPassTemplate = emailResetPassTemplate.replace("{{WITAMY}}", witamyImgB64);
  }

  public void sendEmail(Email email) throws EmailException, IOException, MessagingException {
    log.debug("Email type: {}", email.getEmailType());
    switch (email.getEmailType()) {
      case "newAcc":
        sendRegisterToken(email.getDstEmail(), email.getMessage());
        break;
      case "accConfirmed":
        sendRegisterConfirmation(email.getDstEmail());
        break;
      case "resetPassword":
        sendResetPassword(email.getDstEmail(), email.getMessage());
        break;
      default:
        throw new EmailException("Invalid email type");
    }
    log.debug("Saving email to DB : {}", email);
    emailRepository.save(
        new EmailDB(
            0,
            email.getSrcEmail(),
            email.getDstEmail(),
            email.getSubject(),
            email.getMessage(),
            email.getEmailType(),
            new Date(System.currentTimeMillis())));
  }

  private void sendRegisterToken(String to, String link) throws IOException, MessagingException {
    log.info("sendRegisterToken");
    Map<String, String> values = new HashMap<>();
    values.put("link", link);
    String htmlMsg = StringSubstitutor.replace(emailCreateAccTemplate, values, "{", "}");
    emailServiceInt.sendHtmlMessage(to, "Please confirm your account", htmlMsg);
  }

  private void sendRegisterConfirmation(String to) throws MessagingException {
    log.info("sendRegisterConfirmation");
    emailServiceInt.sendHtmlMessage(to, "Account created", emailConfirmedTemplate);
  }

  private void sendResetPassword(String to, String link) throws MessagingException {
    log.info("sendResetPassword");
    Map<String, String> values = new HashMap<>();
    values.put("link", link);
    String htmlMsg = StringSubstitutor.replace(emailResetPassTemplate, values, "{", "}");
    emailServiceInt.sendHtmlMessage(to, "Reset your password", htmlMsg);
  }
}
