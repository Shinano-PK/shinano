package com.pk.internal.model;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RestockSupply {
  @NotNull Integer id;
  @NotNull String name;
  @NotNull String description;
  @NotNull Integer amount;
  @NotNull Integer request;
}
