package com.pk.repairmentscheduler.models;

import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RepairmentSchedulerInput {
  @NotNull String idPlane;
  @NotNull Integer idUser;
  @NotNull String description;
}
