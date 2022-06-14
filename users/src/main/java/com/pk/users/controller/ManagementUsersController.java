package com.pk.users.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.ValidationException;

import com.pk.users.model.ErrMsg;
import com.pk.users.model.User;
import com.pk.users.service.NewUsersService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/user/check")
  public Boolean findIfUserExists(@RequestParam Integer id) {
    log.trace("findIfUserExists");
    return usersService.findIfUserExists(id);
  }

  @GetMapping("/user/all")
  public List<User> getAllUsers() {
    log.trace("getAllUsers()");
    return usersService.getAllUsers();
  }

  @GetMapping("/user")
  public User getUserByUsername(@RequestParam String username) {
    log.trace("getUserByUsername()");
    return usersService.getUserByUsername(username);
  }

  @GetMapping("/user")
  public User getUserByEmail(@RequestParam String email) {
    log.trace("getUserByEmail()");
    return usersService.getUserByEmail(email);
  }

  @PostMapping("/user")
  public User addUser(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception {
    log.trace("addUser()");
    validateInput(bindingResult);
    return usersService.addUser(user);
  }

  @PutMapping("/user")
  public User updateUser(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception {
    log.trace("updateUser()");
    validateInput(bindingResult);
    return usersService.updateUser(user);
  }

  @DeleteMapping("/user")
  public ErrMsg deleteUser(@RequestParam String email) throws Exception {
    log.trace("deleteUser()");
    return usersService.deleteUser(email);
  }

  private void validateInput(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.debug(bindingResult.getAllErrors().toString());
      List<String> errors =
          bindingResult.getAllErrors().stream()
              .map(
                  error ->
                      "Error: " + ((FieldError) error).getField() + " " + error.getDefaultMessage())
              .collect(Collectors.toList());
      throw new ValidationException(errors.toString());
    }
  }
}
