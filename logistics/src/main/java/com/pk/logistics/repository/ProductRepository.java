package com.pk.logistics.repository;

import java.util.List;

import com.pk.logistics.models.Product;
import com.pk.logistics.models.ProductRequest;

public interface ProductRepository {

  List<Product> getAll();

  Product get(Integer id);

  Integer save(ProductRequest input);

  Boolean update(Product input);

  Boolean delete(Integer id);
}
