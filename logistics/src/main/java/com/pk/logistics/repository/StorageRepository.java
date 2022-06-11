package com.pk.logistics.repository;

import java.util.List;

import com.pk.logistics.model.Storage;
import com.pk.logistics.model.StorageRequest;

public interface StorageRepository {

  List<Storage> getAll();

  Storage get(Integer id);

  Integer save(StorageRequest input);

  Boolean update(Storage input);

  Boolean delete(Integer id);
}
