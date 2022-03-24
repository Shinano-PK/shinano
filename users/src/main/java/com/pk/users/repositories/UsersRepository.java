package com.pk.users.repositories;

import com.pk.users.models.User;

public interface UsersRepository {
  
  User getByUsername(String username);

  User getByEmail(String email);
  
  Integer save(User user);

  Boolean update(User user);

  Boolean deleteById(Integer id);  
}
