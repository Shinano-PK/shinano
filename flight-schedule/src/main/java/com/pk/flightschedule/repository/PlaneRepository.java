package com.pk.flightschedule.repository;

import com.pk.flightschedule.models.Plane;
import com.pk.flightschedule.models.PlaneInput;

public interface PlaneRepository {

  Plane get(String id);

  Integer save(PlaneInput input);

  Boolean update(Plane input);

  Boolean delete(String id);
}
