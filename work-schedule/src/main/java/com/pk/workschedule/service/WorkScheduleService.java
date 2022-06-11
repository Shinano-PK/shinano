package com.pk.workschedule.service;

import com.pk.workschedule.model.WorkSchedule;
import com.pk.workschedule.model.WorkScheduleInput;
import com.pk.workschedule.repository.WorkScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class WorkScheduleService {
  private WorkScheduleRepository workScheduleRepository;
  private RestTemplate restTemplate;

  public WorkSchedule getWorkScheduleById(Integer id) {
    log.trace("getWorkSchedule() called");
    return workScheduleRepository.get(id);
  }

  public Boolean updateWorkSchedule(WorkSchedule input) {
    log.trace("updateWorkSchedule() called");
    return workScheduleRepository.update(input);
  }

  public Integer saveWorkSchedule(WorkScheduleInput input) {
    log.trace("saveWorkSchedule() called");

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
      log.error("getForEntity exception", e);
      return 0;
    }

    return workScheduleRepository.save(input);
  }

  public Boolean deleteWorkSchedule(Integer input) {
    log.trace("deleteWorkSchedule() called");
    return workScheduleRepository.delete(input);
  }
}