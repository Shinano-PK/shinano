package com.pk.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.model.Ticket;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@AllArgsConstructor
@RestController
public class TicketController {
  RestTemplate restTemplate;
  ObjectMapper objectMapper;

  @GetMapping("/ticket/{id}")
  public List<Ticket> getUserTickets(@PathVariable @Valid Integer id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<Ticket>> ticketEntity =
          restTemplate.exchange(
              "http://tickets-service/",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<Ticket>>() {});

      log.debug("Ticket service response: {}", ticketEntity.getBody());
      return ticketEntity.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  @GetMapping("/ticket")
  public List<Ticket> getTickets() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      ResponseEntity<List<Ticket>> ticketEntity =
          restTemplate.exchange(
              "http://ticket-service/ticket",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<List<Ticket>>() {});
      log.debug("Ticket service response: {}", ticketEntity.getBody());
      return ticketEntity.getBody();
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return Collections.emptyList();
    }
  }

  @PostMapping("/ticket")
  public Ticket addTicket(@RequestBody Ticket ticket) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<Ticket> userEntity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(ticket), headers);
      userEntity =
          restTemplate.exchange(
              "http://tickets-service/ticket", HttpMethod.POST, entity, Ticket.class);
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

  @PutMapping("/ticket")
  public Ticket updateTicket(@RequestBody Ticket ticket) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity;
    ResponseEntity<Ticket> userEntity;
    try {
      entity = new HttpEntity<>(objectMapper.writeValueAsString(ticket), headers);
      userEntity =
          restTemplate.exchange(
              "http://tickets-service/ticket", HttpMethod.PUT, entity, Ticket.class);
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

  @DeleteMapping("/ticket/{id}")
  public ErrMsg deleteTicket(@PathVariable Integer id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<ErrMsg> userEntity;
    try {
      userEntity =
          restTemplate.exchange(
              "http://tickets-service/?id={id}", HttpMethod.DELETE, null, ErrMsg.class, id);
    } catch (RestClientException e) {
      log.error("getForEntity exception, e:", e);
      return null;
    }
    log.debug("User service response: {}", userEntity.getBody());
    return userEntity.getBody();
  }
}
