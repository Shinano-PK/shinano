package com.pk.repairmentscheduler.repository;

import com.pk.repairmentscheduler.models.RepairmentScheduler;
import com.pk.repairmentscheduler.models.RepairmentSchedulerInput;
import java.util.List;

public interface RepairmentSchedulerRepository {

  RepairmentScheduler get(Integer id);

  List<RepairmentScheduler> getAll();

  List<RepairmentScheduler> getByPlane(String idPlane);

  List<RepairmentScheduler> getByUser(Integer idUser);

  List<RepairmentScheduler> getByPlaneAndUser(String idPlane, Integer idUser);

  Integer save(RepairmentSchedulerInput input);

  Boolean update(RepairmentScheduler input);

  Boolean delete(Integer id);
}
