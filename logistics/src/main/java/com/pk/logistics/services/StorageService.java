package com.pk.logistics.services;

import com.pk.logistics.models.Storage;
import com.pk.logistics.models.StorageRequest;
import com.pk.logistics.repository.StorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StorageService {
  private StorageRepository storageRepository;

  public Storage getStorageById(Integer id) {
    return storageRepository.get(id);
  }

  public Boolean updateStorage(Storage input) {
    Storage storage = getStorageById(input.getId());
    if (storage == null) {
      return false;
    }
    return storageRepository.update(storage);
  }

  public Integer saveStorage(StorageRequest input) {
    return storageRepository.save(input);
  }

  public Boolean deleteStorage(Integer input) {
    return storageRepository.delete(input);
  }
}
