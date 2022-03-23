package com.pk.repository;

import java.sql.Date;
import java.util.List;

import com.pk.flightschedule.models.FlightSchedule;

public interface FlightScheduleRepository {
  
  public List<FlightSchedule> getPeriod(Date start, Date end);
}
