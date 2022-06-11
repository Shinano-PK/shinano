package com.pk.email.controller;

import com.pk.email.exception.EmailException;
import com.pk.email.model.Email;
import com.pk.email.model.ErrMsg;
import com.pk.email.service.EmailService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class EmailController {
  private EmailService emailService;

  @PostMapping("/email")
  public ErrMsg sendEmail(@RequestBody @Valid Email email, BindingResult bindingResult) {
    validateInput(bindingResult);
    try {
      emailService.sendEmail(email);
      return new ErrMsg("ok");
    } catch (EmailException | IOException | MessagingException e) {
      log.warn("Cannot send email, error: {}", e);
      return new ErrMsg("Cannot send email");
    }
  }

  private void validateInput(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.debug(bindingResult.getAllErrors().toString());
      List<String> errors =
          bindingResult.getAllErrors().stream()
              .map(
                  error ->
                      "Error: " + ((FieldError) error).getField() + " " + error.getDefaultMessage())
              .collect(Collectors.toList());
      throw new ValidationException(errors.toString());
    }
  }
}
