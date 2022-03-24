package com.pk.internal.models;

import lombok.NonNull;
import lombok.Value;

@Value
public class NewPassword {
  @NonNull String login;
  @NonNull String password;
  @NonNull String token;  
}
