package com.pk.flightschedule.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Flight {
  @NotNull Integer id;
  @NotNull String idPlane;
  @NotNull Integer idFlightSchedule;
  @NotNull Integer delay;
  @NotNull String status;
  @NotNull String runway;
}
