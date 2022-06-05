package com.pk.logistics.models;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class StorageRequest {
  @NotNull String type;
  @NotNull Integer capacity;
  @NotNull Integer amount;
}
