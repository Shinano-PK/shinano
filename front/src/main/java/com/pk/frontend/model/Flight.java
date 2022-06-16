package com.pk.frontend.model;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class Flight {
  @NotNull Integer id;
  @NotNull String idPlane;
  @NotNull Integer idFlightSchedule;
  @NotNull Integer delay;
  @NotNull String status;
  @NotNull String runway;
}
