package com.pk.weather.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Weather {
  private Integer id;
  private String main;
  private String description;
  private String icon;
}
