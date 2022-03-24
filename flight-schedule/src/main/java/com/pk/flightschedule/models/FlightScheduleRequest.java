package com.pk.flightschedule.models;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FlightScheduleRequest {
  @NotNull
  Date scheduleStartDate;
  @NotNull
  Date scheduleEndDate;
}
