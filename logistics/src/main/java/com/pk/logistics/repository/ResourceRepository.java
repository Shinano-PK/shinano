package com.pk.logistics.repository;

import java.util.List;

import com.pk.logistics.model.Resource;
import com.pk.logistics.model.ResourceRequest;

public interface ResourceRepository {

  List<Resource> getAll();

  Resource get(Integer id);

  Integer save(ResourceRequest input);

  Boolean update(Resource input);

  Boolean delete(Integer id);
}
