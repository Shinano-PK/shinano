package com.pk.workschedule.model;

import java.sql.Date;
import java.sql.Time;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class WorkSchedule {
  @NotNull Integer id;
  @NotNull Integer idUser;
  @NotNull Integer idTask;
  @NotNull Integer weekday;
  @NotNull Time startTime;
  @NotNull Time endTime;
  @NotNull Date scheduleStartDate;
  @NotNull Date scheduleEndDate;
}
