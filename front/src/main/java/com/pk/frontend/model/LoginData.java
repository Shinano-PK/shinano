package com.pk.frontend.model;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class LoginData {
  @NotNull String login;
  @NotNull String password;
}
