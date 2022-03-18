package com.pk.internal.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.pk.internal.models.ErrMsg;
import com.pk.internal.models.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserManagementController {

  @GetMapping("/allUsers")
  public List<User> getAllUsers() {
    return Collections.emptyList();
  }

  @GetMapping("/user")
  public User getUser(@RequestParam Integer id) {
    return new User("", "email", "password", 1, null, "asadas");
  }

  @PostMapping("/user")
  public User addNewUser(@RequestBody @Valid User user) {
    return user;
  }

  @PutMapping("/user")
  public User updateUser(@RequestBody @Valid User user) {
    return user;
  }

  @DeleteMapping("/user")
  public ErrMsg deleteUser(@RequestParam Integer id) {
    return new ErrMsg("Ok");
  }

  // TODO some kind of find?
}
