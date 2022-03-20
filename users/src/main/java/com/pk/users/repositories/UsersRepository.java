package com.pk.users.repositories;

import com.pk.users.models.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository {
  
  User getByUsername(String username);

  Integer save(User user);

  Boolean update(User user);

  Boolean deleteById(Integer id);  
}
