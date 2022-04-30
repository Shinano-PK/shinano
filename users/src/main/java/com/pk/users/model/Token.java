package com.pk.users.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
  String token;
  // TODO Enum ?
  Date validUntil;
  String type;
}
