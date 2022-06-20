package com.pk.flightschedule.services;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.models.FlightScheduleRequest;
import com.pk.flightschedule.repository.FlightScheduleRepository;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FlightScheduleService {
  private FlightScheduleRepository flightScheduleRepository;

  public List<FlightSchedule> getFlightSchedules() {
    return flightScheduleRepository.getAll();
  }

  public FlightSchedule getFlightScheduleById(Integer id) {
    return flightScheduleRepository.get(id);
  }

  public List<FlightSchedule> getFlightScheduleForDates(FlightScheduleRequest request) {
    if (request.getScheduleEndDate().after(request.getScheduleEndDate())) {
      return Collections.emptyList();
    }

    return flightScheduleRepository.getPeriod(
        request.getScheduleStartDate(), request.getScheduleEndDate());
  }

  public Boolean updateFlightSchedule(FlightSchedule input) {
    return flightScheduleRepository.update(input);
  }

  public FlightSchedule saveFlightSchedule(FlightScheduleInput input) throws Exception {
    FlightSchedule flightSchedule =
        new FlightSchedule(
            flightScheduleRepository.save(input),
            input.getStartWeekday(),
            input.getEndWeekday(),
            input.getStartTime(),
            input.getEndTime(),
            input.getScheduleStartDate(),
            input.getScheduleEndDate(),
            input.getDestination(),
            input.getFrom(),
            input.getKind());
    if (flightSchedule.getId() < 1) {
      throw new Exception("Could not add new flight schedule");
    }
    return flightSchedule;
  }

  public Boolean deleteFlightSchedule(Integer input) {
    return flightScheduleRepository.delete(input);
  }
}
