package com.pk.flightschedule.services;

import java.util.Collections;
import java.util.List;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.models.FlightScheduleRequest;
import com.pk.flightschedule.repository.FlightScheduleRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlightScheduleService {
  private FlightScheduleRepository flightScheduleRepository;

  public List<FlightSchedule> getFlightScheduleForDates(FlightScheduleRequest request) {
    if (request.getScheduleEndDate().after(request.getScheduleEndDate())) {
      return Collections.emptyList();
    }

    return flightScheduleRepository.getPeriod(request.getScheduleStartDate(), request.getScheduleEndDate());
  }

  public Integer saveFlightSchedule(FlightScheduleInput input) {
    return flightScheduleRepository.save(input);
  }
}
