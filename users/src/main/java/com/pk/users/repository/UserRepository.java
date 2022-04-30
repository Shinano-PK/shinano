package com.pk.users.repository;

import com.pk.users.model.User;

public interface UserRepository {
  
  User getByUsername(String username);

  User getByEmail(String email);
  
  Integer save(User user);

  Boolean update(User user);

  Boolean deleteByEmail(String email);
}
