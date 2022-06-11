package com.pk.flightschedule.repository;

import java.util.List;

import com.pk.flightschedule.models.Flight;
import com.pk.flightschedule.models.FlightInput;

public interface FlightRepository {

  Flight get(Integer id);

  List<Flight> getAll();

  List<Flight> getByFlightSchedule(Integer id);

  Integer save(FlightInput input);

  Boolean update(Flight input);

  Boolean delete(Integer id);
}
