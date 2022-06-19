package com.pk.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
  @NotNull Integer id;
  @NotNull String idPlane;
  @NotNull Integer idFlightSchedule;
  @NotNull Integer delay;
  @NotNull String status;
  @NotNull String runway;
}
