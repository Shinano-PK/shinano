package com.pk.workschedule.model;

import java.sql.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

@Value
public class WorkScheduleRequest {
  @NotNull Date scheduleStartDate;
  @NotNull Date scheduleEndDate;
}
