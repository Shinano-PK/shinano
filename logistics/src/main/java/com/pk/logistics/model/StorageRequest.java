package com.pk.logistics.model;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class StorageRequest {
  @NotNull String type;
  @NotNull Integer capacity;
  @NotNull Integer amount;
}
