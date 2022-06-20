package com.pk.flightschedule.services;

import com.pk.flightschedule.models.Flight;
import com.pk.flightschedule.models.FlightControlRequest;
import com.pk.flightschedule.models.FlightInput;
import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.repository.FlightRepository;
import com.pk.flightschedule.repository.FlightScheduleRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FlightService {
  private FlightRepository flightRepository;
  private FlightScheduleRepository flightScheduleRepository;

  public Flight getFlightById(Integer id) {
    return flightRepository.get(id);
  }

  public List<FlightControlRequest> getPlaneControlArrival(Date date) {
    List<FlightControlRequest> list = new ArrayList<>();
    long oneDay = 24 * 60 * 60 * (long) 1000;
    Date before = new Date(date.getTime() - oneDay);
    Date after = new Date(date.getTime() + oneDay);
    List<FlightSchedule> flightSchedules = flightScheduleRepository.getPeriod(before, after);
    for (FlightSchedule flightSchedule : flightSchedules) {
      List<Flight> flights = flightRepository.getByFlightSchedule(flightSchedule.getId());
      for (Flight flight : flights) {
        list.add(
            new FlightControlRequest(
                flight.getId(),
                flightSchedule.getEndTime(),
                flightSchedule.getDestination(),
                flightSchedule.getFrom(),
                flight.getRunway(),
                flight.getStatus()));
      }
    }
    return list;
  }

  public List<FlightControlRequest> getPlaneControlDeparture(Date date) {
    List<FlightControlRequest> list = new ArrayList<>();
    long oneDay = 24 * 60 * 60 * (long) 1000;
    Date before = new Date(date.getTime() - oneDay);
    Date after = new Date(date.getTime() + oneDay);
    List<FlightSchedule> flightSchedules = flightScheduleRepository.getPeriod(before, after);
    for (FlightSchedule flightSchedule : flightSchedules) {
      List<Flight> flights = flightRepository.getByFlightSchedule(flightSchedule.getId());
      for (Flight flight : flights) {
        list.add(
            new FlightControlRequest(
                flight.getId(),
                flightSchedule.getStartTime(),
                flightSchedule.getDestination(),
                flightSchedule.getFrom(),
                flight.getRunway(),
                flight.getStatus()));
      }
    }
    return list;
  }

  public Boolean updateFlight(Flight input) throws Exception {
    Flight real = getFlightById(input.getId());
    Boolean result;
    if (real == null) {
      throw new Exception("No flight to be updated");
    }
    if (!input.getId().equals(real.getId())) {
      throw new Exception("You cannot change ID");
    }
    if (input.getIdFlightSchedule() != null) {
      real.setIdFlightSchedule(input.getIdFlightSchedule());
    }
    if (input.getIdPlane() != null) {
      real.setIdPlane(input.getIdPlane());
    }
    if (input.getDelay() != null) {
      real.setDelay(input.getDelay());
    }
    if (input.getStatus() != null) {
      real.setStatus(input.getStatus());
    }
    if (input.getRunway() != null) {
      real.setRunway(input.getRunway());
    }
    if (Boolean.FALSE.equals(flightRepository.update(real))) {
      throw new Exception("Update failed");
    }

    return true;
  }

  public Integer saveFlight(FlightInput input) {
    return flightRepository.save(input);
  }

  public Boolean deleteFlight(Integer input) {
    return flightRepository.delete(input);
  }
}
