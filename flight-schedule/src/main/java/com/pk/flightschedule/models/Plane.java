package com.pk.flightschedule.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Plane {
  @NotNull String id;
  @NotNull Integer firstClassCapacity;
  @NotNull Integer secondClassCapacity;
  @NotNull Integer carryingCapacity;
  @NotNull String owner;
}
