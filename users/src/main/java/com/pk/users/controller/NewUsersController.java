package com.pk.users.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.netflix.config.validation.ValidationException;
import com.pk.users.model.ErrMsg;
import com.pk.users.model.NewPassword;
import com.pk.users.model.User;
import com.pk.users.service.NewUsersService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/new")
public class NewUsersController {
  NewUsersService newUsersService;

  @PostMapping("/user")
  public ErrMsg newUser(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception {
    validateInput(bindingResult);
    newUsersService.addUser(user);
    return new ErrMsg("ok");
  }
  
  @PutMapping("/user/resetPassword")
  public ErrMsg resetPassword(@RequestBody String email) throws Exception {
    if (email == null || email.isEmpty()) {
      throw new Exception("Invalid email provided");
    }
    return newUsersService.resetPassword(email);
  }
  
  @PutMapping("/user/resetPasswordStage2")
  public ErrMsg resetPasswordStage2(@RequestBody @Valid NewPassword newPassword, BindingResult bindingResult) throws Exception {
    validateInput(bindingResult);
    return newUsersService.resetPasswordStage2(newPassword);
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
