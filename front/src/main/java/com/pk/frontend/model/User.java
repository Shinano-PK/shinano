package com.pk.frontend.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  public String username;
  public String email;
  public String password;
  public Integer enabled;
  public Date created;
  public String authority;
}
