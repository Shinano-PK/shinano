package com.pk.repairmentscheduler.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepairmentScheduler {
  @NotNull Integer id;
  @NotNull String idPlane;
  @NotNull Integer idUser;
  @NotNull String description;
}
