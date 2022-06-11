package com.pk.flightschedule.repository;

import com.pk.flightschedule.models.Plane;
import java.util.List;

public interface PlaneRepository {

  Plane get(String id);

  List<Plane> getAll();

  String save(Plane input);

  Boolean update(Plane input);

  Boolean delete(String id);
}
