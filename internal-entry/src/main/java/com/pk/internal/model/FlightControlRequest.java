package com.pk.internal.model;

import java.sql.Time;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightControlRequest {
  @NotNull Integer idFlight;
  @NotNull Time time;
  @NotNull String destination;
  @NotNull String from;
  @NotNull String runway;
  @NotNull String status;
}
