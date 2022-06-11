package com.pk.logistics.model;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Product {
  @NotNull Integer id;
  @NotNull String name;
  @NotNull String producer;
  @NotNull String outsideId;
  @NotNull String description;
  @NotNull Double price;
  @NotNull String unit;
  @NotNull String type;
}
