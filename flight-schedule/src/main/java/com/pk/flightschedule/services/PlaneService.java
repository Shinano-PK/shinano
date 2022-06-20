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

  public Boolean updatePlane(Plane input) throws Exception {
    Plane real = planeRepository.get(input.getId());
    if (input.getId() == null) {
      throw new Exception("Invalid id");
    }
    if (input.getId().toUpperCase().equals(real.getId())) {
      throw new Exception("Cannot change id");
    }
    real.setId(input.getId().toUpperCase());
    if (input.getOwner() != null) {
      real.setOwner(input.getOwner());
    }
    if (input.getCarryingCapacity() != null) {
      real.setCarryingCapacity(input.getCarryingCapacity());
    }
    if (input.getFirstClassCapacity() != null) {
      real.setFirstClassCapacity(input.getFirstClassCapacity());
    }
    if (input.getSecondClassCapacity() != null) {
      real.setSecondClassCapacity(input.getSecondClassCapacity());
    }

    return planeRepository.update(real);
  }

  public String savePlane(Plane input) {
    return planeRepository.save(input);
  }

  public Boolean deletePlane(String input) {
    return planeRepository.delete(input);
  }
}
