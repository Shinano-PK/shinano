package com.pk.flightschedule.models;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class FlightScheduleRequest {
  @NotNull
  Date scheduleStartDate;
  @NotNull
  Date scheduleEndDate;
}
