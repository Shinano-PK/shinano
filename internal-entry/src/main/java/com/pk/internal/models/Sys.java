package com.pk.internal.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sys {
  private Integer type;
  private Integer id;
  private String country;
  private Integer sunrise;
  private Integer sunset;
}
