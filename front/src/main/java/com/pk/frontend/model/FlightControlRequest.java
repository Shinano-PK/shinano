package com.pk.frontend.model;

import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FlightControlRequest {
  public Integer idFlight;
  public Time time;
  public String destination;
  public String from;
  public String runway;
  public String status;
}
