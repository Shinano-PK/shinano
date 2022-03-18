package com.pk.weather.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main {
  private Double temp;
  private Double feelsLike;
  private Double tempMin;
  private Double tempMax;
  private Integer pressure;
  private Integer humidity;
}
