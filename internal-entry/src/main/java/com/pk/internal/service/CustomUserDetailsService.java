package com.pk.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  ObjectMapper objectMapper;
  RestTemplate restTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // log in via users microservice
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<com.pk.internal.models.User> userEntity =
        restTemplate.getForEntity(
            "http://users-service/manage/user?username={username}",
            com.pk.internal.models.User.class,
            username);
    log.debug("User service response: {}", userEntity.getBody());
    com.pk.internal.models.User user = userEntity.getBody();
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    log.debug("Got user: {}", user);
    return new User(
        user.getUsername(),
        user.getPassword(),
        Arrays.asList(new SimpleGrantedAuthority(user.getAuthority())));
  }
}
