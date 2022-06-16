package com.pk.logistics.service;

import com.pk.logistics.model.Product;
import com.pk.logistics.model.Resource;
import com.pk.logistics.model.RestockSupply;
import com.pk.logistics.repository.ProductRepository;
import com.pk.logistics.repository.ResourceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogisticsService {
  private ProductRepository productRepository;
  private ResourceRepository resourceRepository;

  public List<RestockSupply> getRestockResuply() {
    List<RestockSupply> result = new ArrayList<>();
    Product product;
    List<Resource> resources = resourceRepository.getAll();
    Boolean found = false;
    RestockSupply reference = null;
    for (Resource resource : resources) {
      for (RestockSupply restockSupply : result) {
        if (resource.getId().equals(restockSupply.getId())) {
          found = true;
          reference = restockSupply;
          break;
        }
      }
      if (Boolean.TRUE.equals(found)) {
        reference.setAmount(reference.getAmount() + resource.getQuantity());
        found = false;
      } else {
        product = productRepository.get(resource.getIdProduct());
        result.add(
            new RestockSupply(
                resource.getIdProduct(),
                product.getName(),
                product.getDescription(),
                resource.getQuantity(),
                0));
      }
    }
    return result;
  }

  public List<RestockSupply> resupply(List<RestockSupply> list) {
    Resource resource;
    for (RestockSupply element : list) {
      resource = resourceRepository.get(element.getId());
      resourceRepository.update(
          new Resource(
              resource.getId(),
              resource.getIdProduct(),
              resource.getIdStorage(),
              resource.getQuantity() + element.getAmount()));
    }
    return list;
  }
}
