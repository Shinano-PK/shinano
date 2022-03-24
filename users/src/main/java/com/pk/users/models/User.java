package com.pk.users.models;

import java.sql.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  @NotNull String name;
  @NotNull String surname;
  @NotNull Date birthDate;
  Integer enabled;
  Date created;
  @NotNull String email;
  @NotNull String login;
  @NotNull String password;
  String authority;
  String token;
}
