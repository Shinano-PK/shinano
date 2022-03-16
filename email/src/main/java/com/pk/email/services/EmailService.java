package com.pk.email.services;

import com.pk.email.models.Email;
import java.io.IOException;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
  private EmailServiceInternal emailServiceInt;
  private String emailCreateAccTemplate;
  private String emailConfirmedTemplate;
  private String emailResetPassTemplate;
  private String emailMessageTemplate;
  private String emailMessageToStaffTemplate;

  public EmailService(EmailServiceInternal emailServiceInternal, ResourceLoader resourceLoader)
      throws IOException {
    this.emailServiceInt = emailServiceInternal;
    // emailCreateAccTemplate =
    //     CharStreams.toString(
    //         new InputStreamReader(
    //
    // resourceLoader.getResource("classpath:email_createAccount.html").getInputStream(),
    //             StandardCharsets.UTF_8));
    // emailConfirmedTemplate =
    //     CharStreams.toString(
    //         new InputStreamReader(
    //             resourceLoader.getResource("classpath:email_confirmation.html").getInputStream(),
    //             StandardCharsets.UTF_8));
    // emailResetPassTemplate =
    //     CharStreams.toString(
    //         new InputStreamReader(
    //
    // resourceLoader.getResource("classpath:email_passwordReset.html").getInputStream(),
    //             StandardCharsets.UTF_8));
    // emailMessageToStaffTemplate =
    //     CharStreams.toString(
    //         new InputStreamReader(
    //             resourceLoader.getResource("classpath:message.html").getInputStream(),
    //             StandardCharsets.UTF_8));
    // emailMessageTemplate =
    //     CharStreams.toString(
    //         new InputStreamReader(
    //             resourceLoader.getResource("classpath:message_to_staff.html").getInputStream(),
    //             StandardCharsets.UTF_8));

    // log.info(
    //     "emailCreateAcc:              {} (first 500 chars)",
    //     emailCreateAccTemplate.substring(0, 500));
    // log.info(
    //     "emailConfirmed:              {} (first 500 chars)",
    //     emailConfirmedTemplate.substring(0, 500));
    // log.info(
    //     "emailResetPass:              {} (first 500 chars)",
    //     emailResetPassTemplate.substring(0, 500));
    // log.info(
    //     "emailMessageTemplate:        {} (first 500 chars)",
    //     emailMessageTemplate.substring(0, 500));
    // log.info(
    //     "emailMessageToStaffTemplate: {} (first 500 chars)",
    //     emailMessageToStaffTemplate.substring(0, 500));
  }

  public void sendEmail(Email email) throws Exception {
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
        throw new Exception("Invalid email type");
    }
  }

  private void sendRegisterToken(String to, String link) throws IOException, MessagingException {
    log.info("sendRegisterToken");
    // Map<String, String> values = new HashMap<>();
    // values.put("link", link);
    // String htmlMsg = StringSubstitutor.replace(emailCreateAccTemplate, values, "{", "}");
    // emailServiceInt.sendHtmlMessage(to, "Please confirm your account", htmlMsg);
  }

  private void sendRegisterConfirmation(String to) throws MessagingException {
    log.info("sendRegisterConfirmation");
    // emailServiceInt.sendHtmlMessage(to, "Account created", emailConfirmedTemplate);
  }

  private void sendResetPassword(String to, String link) throws MessagingException {
    log.info("sendResetPassword");
    // Map<String, String> values = new HashMap<>();
    // values.put("link", link);
    // String htmlMsg = StringSubstitutor.replace(emailResetPassTemplate, values, "{", "}");
    // emailServiceInt.sendHtmlMessage(to, "Reset your password", htmlMsg);
  }

  // public void sendMessage(
  //     String to, String subject, String usernameTarget, String usernameSource, String message)
  //     throws MessagingException {
  // Map<String, String> values = new HashMap<>();
  // values.put("usernameTarget", usernameTarget);
  // values.put("usernameSource", usernameSource);
  // values.put("message", message);
  // String htmlMsg = StringSubstitutor.replace(emailMessageTemplate, values, "{", "}");
  // emailServiceInt.sendHtmlMessage(to, subject, htmlMsg);
  // }

  // public void sendMessageToStaff(String to, String subject, String usernameSource, String
  // message)
  //     throws MessagingException {
  //   Map<String, String> values = new HashMap<>();
  //   values.put("usernameSource", usernameSource);
  //   values.put("message", message);
  //   String htmlMsg = StringSubstitutor.replace(emailMessageTemplate, values, "{", "}");
  //   emailServiceInt.sendHtmlMessage(to, subject, htmlMsg);
  // }
}
