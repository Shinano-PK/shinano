package com.pk.frontend.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthResp {
  public String jwt;
  public String role;
}
