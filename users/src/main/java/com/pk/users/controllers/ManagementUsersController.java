package com.pk.users.controllers;

import com.pk.users.models.ErrMsg;
import com.pk.users.models.User;
import com.pk.users.services.NewUsersService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/manage")
public class ManagementUsersController {
  NewUsersService usersService;

  @GetMapping("/user")
  public User getUserByUsername(@RequestParam String username) {
    log.trace("getUserById()");
    return usersService.getUserByUsername(username);
  }

  @PostMapping("/user")
  public User addUser(User user) throws Exception {
    log.trace("addUser()");
    return usersService.addUser(user);
  }

  @PutMapping("/user")
  public User updateUser(User user) throws Exception {
    log.trace("updateUser()");
    return usersService.updateUser(user);
  }

  @DeleteMapping("/user")
  public ErrMsg deleteUser(Integer id) throws Exception {
    log.trace("deleteUser()");
    return usersService.deleteUser(id);
  }
}
