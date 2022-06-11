package com.pk.flightschedule.repository;

import java.sql.Date;
import java.util.List;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;

public interface FlightScheduleRepository {

  FlightSchedule get(Integer id);

  List<FlightSchedule> getAll();

  List<FlightSchedule> getPeriod(Date start, Date end);

  Integer save(FlightScheduleInput input);

  Boolean update(FlightSchedule input);

  Boolean delete(Integer id);
}
