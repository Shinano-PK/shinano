package com.pk.logistics.models;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ResourceRequest {
  @NotNull Integer idProduct;
  @NotNull Integer idStorage;
  @NotNull Integer quantity;
}