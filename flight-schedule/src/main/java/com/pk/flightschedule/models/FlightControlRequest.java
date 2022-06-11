package com.pk.flightschedule.models;

import java.sql.Time;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightControlRequest {
  @NotNull Integer idFlight;
  @NotNull Time time;
  @NotNull String runway;
  @NotNull String status;
}
