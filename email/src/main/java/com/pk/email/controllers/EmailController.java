package com.pk.email.controllers;

import javax.validation.Valid;

import com.pk.email.models.Email;
import com.pk.email.models.ErrMsg;
import com.pk.email.services.EmailService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EmailController {
  private EmailService emailService;

  // TODO Post,Put,Delete ???

  @PostMapping("/sendEmail")
  public ErrMsg sendEmail(@RequestBody @Valid Email email) {
    try {
      emailService.sendEmail(email);
      return new ErrMsg("Ok");
      // TODO make it specific
    } catch (Exception e) {
      return new ErrMsg("Cannot send email");
    }
  }
}
