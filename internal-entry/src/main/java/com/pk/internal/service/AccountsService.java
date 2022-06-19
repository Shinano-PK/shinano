package com.pk.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.AuthResp;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.model.LoginData;
import com.pk.internal.model.User;
import com.pk.internal.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class AccountsService {
  private JwtUtil jwtUtil;
  private UserDetailsService userDetailsService;
  private AuthenticationManager authManager;
  private ObjectMapper objectMapper;
  private RestTemplate restTemplate;

  public AuthResp login(LoginData loginData) throws Exception {
    log.trace("login() called");
    try {
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginData.getLogin(), loginData.getPassword()));
      log.info("valid creds called");
    } catch (
        org.springframework.security.authentication.InternalAuthenticationServiceException ignore) {
      log.error("Error: ", ignore);
      throw new BadCredentialsException("Cannot auth");
    }
    User user = getUser(loginData.getLogin());
    if (user == null) {
      log.info("User is null");
      // TODO do sth
    }
    if (user.getEnabled() == 0) {
      log.info("User not enabled");
      // TODO do sth
      // throw new EmailNotConfirmed();
    }
    log.debug("account active");

    final UserDetails uDetails = userDetailsService.loadUserByUsername(loginData.getLogin());
    final String jwt = jwtUtil.generateToken(uDetails);
    log.info("token created");
    return new AuthResp(jwt, user.getAuthority());
  }

  // TODO code duplication, copied from CustomUserDetailsService
  private User getUser(String username) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<com.pk.internal.model.User> userEntity =
        restTemplate.getForEntity(
            "http://users-service/manage/userByUsername?username={username}",
            com.pk.internal.model.User.class,
            username);
    log.debug("User service response: {}", userEntity.getBody());
    return userEntity.getBody();
  }

  public ErrMsg register(User user) throws Exception {
    log.trace("register() called");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      // Rewrap it to another exception
      throw new Exception("Login data is malformed");
    }
    ResponseEntity<ErrMsg> response =
        restTemplate.exchange(
            "http://users-service/new/user", HttpMethod.POST, entity, ErrMsg.class);
    if (response.getBody() == null) {
      throw new Exception("body is null, user service malfunctioning");
    }
    if (response.getBody().getMessage().equalsIgnoreCase("err")) {
      throw new Exception("user service returned error message");
    }
    return response.getBody();
  }

  public ErrMsg resetPassword(String login) throws Exception {
    log.trace("resetPassword() called");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(login), headers);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // Rewrap it to another exception
      throw new Exception("Login data is malformed");
    }
    ResponseEntity<ErrMsg> response =
        restTemplate.exchange(
            "http://users-service/resetPassword", HttpMethod.POST, entity, ErrMsg.class);
    if (response.getBody() == null) {
      throw new Exception("body is null, user service malfunctioning");
    }
    if (response.getBody().getMessage().equalsIgnoreCase("err")) {
      throw new Exception("user service returned error message");
    }
    return response.getBody();
  }

  public ErrMsg resetPasswordStage2(String token, String newPassword) {
    log.trace("resetPasswordStage2() called");
    return new ErrMsg("// TODO temp val");
  }
}
