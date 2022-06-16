package com.pk.internal.model;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Flight {
  @NotNull Integer id;
  @NotNull String idPlane;
  @NotNull Integer idFlightSchedule;
  @NotNull Integer delay;
  @NotNull String status;
  @NotNull String runway;
}
