package com.pk.users.models;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Email {
  @NotNull String srcEmail;
  @NotNull String dstEmail;
  @NotNull String subject;
  @NotNull String message;
  // TODO enum?
  @NotNull String emailType;
}
