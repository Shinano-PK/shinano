package com.pk.internal.models;

import javax.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

import lombok.Value;

@Value
public class Authority implements GrantedAuthority {
  @NotNull String username;
  @NotNull String authority;
}
