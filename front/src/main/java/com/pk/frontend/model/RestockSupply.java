package com.pk.frontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestockSupply {
  public Integer id;
  public String name;
  public String description;
  public Integer amount;
  public Integer request;
}
