package com.pk.internal.controllers;

import com.pk.internal.models.ErrMsg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new")
public class NewUsersController {
  @GetMapping("/login")
  public ErrMsg login() {
    return new ErrMsg("login called");
  }

  @PostMapping("/register")
  public ErrMsg register() {
    return new ErrMsg("register called");
  }

  @PostMapping("/resetPassword")
  public ErrMsg resetPassword() {
    return new ErrMsg("resetPassword called");
  }
}
