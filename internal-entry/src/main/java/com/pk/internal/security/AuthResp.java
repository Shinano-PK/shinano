package com.pk.internal.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResp {
  private String jwt;
  private String role;
}
