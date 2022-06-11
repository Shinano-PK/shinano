package com.pk.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.model.User;
import com.pk.internal.service.UserManagementService;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/management")
@AllArgsConstructor
public class UserManagementController {
  UserManagementService userManagementService;

  @GetMapping("/allUsers")
  public List<User> getAllUsers() {
    return Collections.emptyList();
  }

  // TODO add param validation
  @GetMapping("/user")
  public User getUser(@RequestParam String email) {
    return userManagementService.getUser(email);
  }

  // TODO add param validation
  @PostMapping("/user")
  public User addNewUser(@RequestBody @Valid User user, BindingResult bindResult) throws Exception {
    return userManagementService.addNewUser(user);
  }

  // TODO add param validation
  @PutMapping("/user")
  public User updateUser(@RequestBody @Valid User user, BindingResult bindResult) throws Exception {
    return userManagementService.updateUser(user);
  }

  // TODO add param validation
  @DeleteMapping("/user")
  public ErrMsg deleteUser(@RequestParam String email) {
    return userManagementService.deleteUser(email);
  }

  // TODO some kind of find?
}
