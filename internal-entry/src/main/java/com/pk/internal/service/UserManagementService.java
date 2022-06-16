package com.pk.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.model.User;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class UserManagementService {
  ObjectMapper objectMapper;
  RestTemplate restTemplate;

  public List<User> getAllUsers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<User>> ticketEntity =
          restTemplate.exchange(
              "http://users-service/manage/user/all",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<User>>() {});

      log.debug("User service response: {}", ticketEntity.getBody());
      return ticketEntity.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  public User getUser(String email) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<User> userEntity =
          restTemplate.getForEntity(
              "http://users-service/manage/userByEmail?email={email}", User.class, email);
      log.debug("User service response: {}", userEntity.getBody());
      return userEntity.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
  }

  public User addNewUser(User user) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<User> userEntity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
      userEntity =
          restTemplate.exchange(
              "http://users-service/manage/user", HttpMethod.POST, entity, User.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      return null;
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
    log.debug("User service response: {}", userEntity.getBody());
    return userEntity.getBody();
  }

  public User updateUser(User user) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<User> userEntity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
      userEntity =
          restTemplate.exchange(
              "http://users-service/manage/user", HttpMethod.PUT, entity, User.class);
    } catch (JsonProcessingException e) {
      log.error("Json processing error", e);
      return null;
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
    log.debug("User service response: {}", userEntity.getBody());
    return userEntity.getBody();
  }

  public ErrMsg deleteUser(String email) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<ErrMsg> errMsg =
          restTemplate.exchange(
              "http://users-service/manage/user?email={email}",
              HttpMethod.DELETE,
              null,
              ErrMsg.class,
              email);
      log.debug("User service response: {}", errMsg.getBody());
      return errMsg.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
  }
}
