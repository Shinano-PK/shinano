package com.pk.logistics.models;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Resource {
  @NotNull Integer id;
  @NotNull Integer idProduct;
  @NotNull Integer idStorage;
  @NotNull Integer quantity;
}
