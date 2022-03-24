package com.pk.users.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
  String token;
  // TODO Enum ?
  String type;
  Date validUntil;
}
