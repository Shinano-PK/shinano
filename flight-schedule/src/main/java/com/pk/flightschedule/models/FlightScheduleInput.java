package com.pk.flightschedule.models;

import java.sql.Date;
import java.sql.Time;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class FlightScheduleInput {
  @NotNull
  Integer startWeekday;
  @NotNull
  Integer endWeekday;
  @NotNull
  Time startTime;
  @NotNull
  Time endTime;
  @NotNull
  Date scheduleStartDate;
  @NotNull
  Date scheduleEndDate;
  @NotNull
  String destination;
  @NotNull
  String from;
  @NotNull
  String kind;
}
