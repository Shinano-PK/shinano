package com.pk.logistics.repository;

import java.util.List;

import com.pk.logistics.models.Resource;
import com.pk.logistics.models.ResourceRequest;

public interface ResourceRepository {

  List<Resource> getAll();

  Resource get(Integer id);

  Integer save(ResourceRequest input);

  Boolean update(Resource input);

  Boolean delete(Integer id);
}
