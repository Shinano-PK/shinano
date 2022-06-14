package com.pk.users.repository;

import java.util.List;

import com.pk.users.model.User;

public interface UserRepository {
  User getById(Integer id);

  List<User> getAllUsers();

  User getByUsername(String username);

  User getByEmail(String email);
  
  Integer save(User user);

  Boolean update(User user);

  Boolean deleteByEmail(String email);
}
