package com.pk.logistics.services;

import com.pk.logistics.models.Resource;
import com.pk.logistics.models.ResourceRequest;
import com.pk.logistics.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResourceService {
  private ResourceRepository resourceRepository;

  public Resource getResourceById(Integer id) {
    return resourceRepository.get(id);
  }

  public Boolean updateResource(Resource input) {
    Resource resource = getResourceById(input.getId());
    if (resource == null) {
      return false;
    }
    return resourceRepository.update(resource);
  }

  public Integer saveResource(ResourceRequest input) {
    return resourceRepository.save(input);
  }

  public Boolean deleteResource(Integer input) {
    return resourceRepository.delete(input);
  }
}
