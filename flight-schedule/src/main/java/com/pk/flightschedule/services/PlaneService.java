package com.pk.flightschedule.services;

import com.pk.flightschedule.models.Plane;
import com.pk.flightschedule.repository.PlaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaneService {
  private PlaneRepository planeRepository;

  public Plane getPlaneById(String id) {
    return planeRepository.get(id);
  }

  public Boolean updatePlane(Plane input) {
    return planeRepository.update(input);
  }

  public String savePlane(Plane input) {
    return planeRepository.save(input);
  }

  public Boolean deletePlane(String input) {
    return planeRepository.delete(input);
  }
}
