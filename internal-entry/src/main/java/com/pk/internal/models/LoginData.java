package com.pk.internal.models;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class LoginData {
  @NotNull String login;
  @NotNull String password;
}
