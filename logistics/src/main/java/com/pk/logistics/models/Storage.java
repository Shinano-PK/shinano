package com.pk.logistics.models;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class Storage {
  @NotNull Integer id;
  @NotNull String type;
  @NotNull Integer capacity;
  @NotNull Integer amount;
}
