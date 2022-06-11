package com.pk.users.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class NewPassword {
  @NonNull String login;
  @NonNull String password;
  @NonNull String token;  
}
