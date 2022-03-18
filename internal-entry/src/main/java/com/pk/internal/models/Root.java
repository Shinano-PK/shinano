package com.pk.internal.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Root {
  private Coord coord;
  private List<Weather> weather = new ArrayList<>();
  private String base;
  private Main main;
  private Integer visibility;
  private Wind wind;
  private Clouds clouds;
  private Integer dt;
  private Sys sys;
  private Integer timezone;
  private Integer id;
  private String name;
  private Integer cod;
}
