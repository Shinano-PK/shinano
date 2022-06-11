package com.pk.repairmentscheduler.services;

import com.pk.repairmentscheduler.models.RepairmentScheduler;
import com.pk.repairmentscheduler.models.RepairmentSchedulerInput;
import com.pk.repairmentscheduler.repository.RepairmentSchedulerRepository;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RepairmentSchedulerService {
  private static final String GET_FOR_ENTITY_EXCEPTION = "getForEntity exception, e:";
  private RepairmentSchedulerRepository flightScheduleRepository;
  private RestTemplate restTemplate;

  public RepairmentScheduler getRepairmentSchedulerById(Integer id) {
    return flightScheduleRepository.get(id);
  }

  public List<RepairmentScheduler> getRepairmentSchedulerByPlaneAndUser(
      String idPlane, Integer idUser) {
    if (idPlane == null && idUser == null) {
      return Collections.emptyList();
    } else if (idPlane == null || idPlane.isBlank()) {
      return flightScheduleRepository.getByUser(idUser);
    } else if (idUser == null) {
      return flightScheduleRepository.getByPlane(idPlane);
    }
    return flightScheduleRepository.getByPlaneAndUser(idPlane, idUser);
  }

  public Boolean updateRepairmentScheduler(RepairmentScheduler input) {
    RepairmentScheduler schedule = getRepairmentSchedulerById(input.getId());
    if (schedule == null) {
      return false;
    }
    String idPlane = input.getIdPlane();
    Integer idUser = input.getIdUser();
    if (idPlane == null || idPlane.isBlank()) {
      idPlane = schedule.getIdPlane();
    } else {
      // If plane is given from outside, then check if it exists
      try {
        ResponseEntity<Boolean> planeEntity =
            restTemplate.getForEntity(
                "http://flight-schedule-service/plane/check?id={id}",
                Boolean.class,
                input.getIdPlane());
        log.debug("Plane schedule service response: {}", planeEntity.getBody());
        if (Boolean.FALSE.equals(planeEntity.getBody())) {
          return false;
        }

      } catch (RestClientException e) {
        log.error(GET_FOR_ENTITY_EXCEPTION, e);
        return false;
      }
    }
    if (idUser == null) {
      idUser = schedule.getIdUser();
    } else {
      try {
        ResponseEntity<Boolean> userEntity =
            restTemplate.getForEntity(
                "http://users-service/manage/user/check?userId={userId}",
                Boolean.class,
                input.getIdUser());
        log.debug("User service response: {}", userEntity.getBody());
        if (Boolean.FALSE.equals(userEntity.getBody())) {
          return false;
        }

      } catch (RestClientException e) {
        log.error(GET_FOR_ENTITY_EXCEPTION, e);
        return false;
      }
    }

    schedule.setId(input.getId());
    schedule.setIdPlane(idPlane);
    schedule.setIdUser(idUser);
    return flightScheduleRepository.update(schedule);
  }

  public Integer saveRepairmentScheduler(RepairmentSchedulerInput input) {

    try {
      ResponseEntity<Boolean> userEntity =
          restTemplate.getForEntity(
              "http://users-service/manage/user/check?userId={userId}",
              Boolean.class,
              input.getIdUser());
      log.debug("User service response: {}", userEntity.getBody());
      if (Boolean.FALSE.equals(userEntity.getBody())) {
        return null;
      }

    } catch (RestClientException e) {
      log.error(GET_FOR_ENTITY_EXCEPTION, e);
      return null;
    }

    try {
      ResponseEntity<Boolean> planeEntity =
          restTemplate.getForEntity(
              "http://flight-schedule-service/plane/check?id={id}",
              Boolean.class,
              input.getIdPlane());
      log.debug("Flight schedule service response: {}", planeEntity.getBody());
      if (Boolean.FALSE.equals(planeEntity.getBody())) {
        return null;
      }

    } catch (RestClientException e) {
      log.error(GET_FOR_ENTITY_EXCEPTION, e);
      return null;
    }
    return flightScheduleRepository.save(input);
  }

  public Boolean deleteRepairmentScheduler(Integer input) {
    return flightScheduleRepository.delete(input);
  }
}
