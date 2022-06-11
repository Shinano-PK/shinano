package com.pk.logistics.services;

import com.pk.logistics.models.Product;
import com.pk.logistics.models.ProductRequest;
import com.pk.logistics.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
  private ProductRepository productRepository;

  public Product getProductById(Integer id) {
    return productRepository.get(id);
  }

  public Boolean updateProduct(Product input) {
    Product resource = getProductById(input.getId());
    if (resource == null) {
      return false;
    }
    return productRepository.update(resource);
  }

  public Integer saveProduct(ProductRequest input) {
    return productRepository.save(input);
  }

  public Boolean deleteProduct(Integer input) {
    return productRepository.delete(input);
  }
}
