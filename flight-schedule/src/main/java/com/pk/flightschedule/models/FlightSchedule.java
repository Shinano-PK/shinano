package com.pk.flightschedule.models;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;

@Data
public class FlightSchedule {
  Integer id;
  Integer startWeekday;
  Integer endWeekday;
  Time startTime;
  Time endTime;
  Date scheduleStartDate;
  Date scheduleEndDate;
  String destination;
  String from;
  String kind;
}
