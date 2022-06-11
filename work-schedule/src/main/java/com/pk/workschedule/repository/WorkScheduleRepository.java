package com.pk.workschedule.repository;

import com.pk.workschedule.model.WorkSchedule;
import com.pk.workschedule.model.WorkScheduleInput;

public interface WorkScheduleRepository {

  WorkSchedule get(Integer id);

  Integer save(WorkScheduleInput input);

  Boolean update(WorkSchedule input);

  Boolean delete(Integer id);
}
