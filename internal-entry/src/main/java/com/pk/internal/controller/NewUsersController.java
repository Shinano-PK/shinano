package com.pk.internal.controller;

import javax.validation.Valid;

import com.pk.internal.model.AuthResp;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.model.LoginData;
import com.pk.internal.model.NewPassword;
import com.pk.internal.model.User;
import com.pk.internal.service.AccountsService;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/new")
@AllArgsConstructor
public class NewUsersController {
  AccountsService accountsService;

  @PostMapping("/login")
  public AuthResp login(@RequestBody @Valid LoginData loginData, BindingResult bindResult) throws Exception {
    log.trace("login() called");
    return accountsService.login(loginData);
  }

  @PostMapping("/register")
  public ErrMsg register(@RequestBody @Valid User user, BindingResult bindResult) throws Exception {
    log.trace("register() called");
    return accountsService.register(user);
  }

  @PostMapping("/resetPassword")
  public ErrMsg resetPassword() throws Exception {
    log.trace("resetPassword() called");
    return accountsService.resetPassword(getCallersUsername());
  }

  @PutMapping("/resetPasswordStage2")
  public ErrMsg resetPasswordStage2(@RequestBody NewPassword newPassword, ValidationErrors errors) {
    return accountsService.resetPasswordStage2(newPassword.getToken(), newPassword.getPassword());
  }

  private String getCallersUsername() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    } else {
      return principal.toString();
    }
  }
}
