package com.pk.logistics.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestockSupply {
  @NotNull Integer id;
  @NotNull String name;
  @NotNull String description;
  @NotNull Integer amount;
  @NotNull Integer request;
}
