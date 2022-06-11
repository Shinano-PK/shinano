package com.pk.users.model;

import java.sql.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  Integer userId;
  @NotNull String name;
  @NotNull String surname;
  @NotNull Date birthDate;
  Integer enabled;
  Date created;
  @NotNull String email;
  @NotNull String username;
  @NotNull String password;
  String authority;
  String token;
}
