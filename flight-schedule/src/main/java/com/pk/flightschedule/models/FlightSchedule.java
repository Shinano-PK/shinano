package com.pk.flightschedule.models;

import java.sql.Date;
import java.sql.Time;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightSchedule {
  @NotNull
  Integer id;
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
