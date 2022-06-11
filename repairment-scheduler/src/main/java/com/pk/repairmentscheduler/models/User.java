package com.pk.repairmentscheduler.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  String username;
  String email;
  String password;
  Integer enabled;
  Date created;
  String authority;
}
