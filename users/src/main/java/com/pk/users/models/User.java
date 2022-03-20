package com.pk.users.models;

import java.sql.Date;
import lombok.Value;

@Value
public class User {
  String username;
  String email;
  String password;
  Integer enabled;
  Date created;
  String authority;
}
