package com.pk.users.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.users.models.ErrMsg;
import com.pk.users.models.User;
import com.pk.users.repositories.UsersRepository;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService {
  UsersRepository usersRepository;
  RestTemplate restTemplate;
  ObjectMapper objectMapper;

  public User getUserByUsername(String username) {
    return usersRepository.getByUsername(username);
  }

  public User addUser(User user) throws Exception {
    // validate input
    String email = user.getEmail();
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    if (!Pattern.matches(regex, email)) {
      // throw new InvalidEmail();
      log.info("Invalid email provided: {}", email);
      throw new Exception("Invalid email provided");
    }

    // add user to db
    if (usersRepository.save(user) == 0) {
      throw new Exception("Cannot add new user");
    }

    // send email
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
    ResponseEntity<ErrMsg> response = restTemplate.exchange("http://email/email", HttpMethod.PUT, entity, ErrMsg.class);
    if (response.getBody() == null) {
      throw new Exception("body is null, email service malfunctioning");
    }
    if (response.getBody().getMessage().equalsIgnoreCase("err")) {
      throw new Exception("email service returned error message");
    }
    return user;
  }

  public User updateUser(User user) throws Exception {
    if (Boolean.FALSE.equals(usersRepository.update(user))) {
      throw new Exception("cannot update user");
    }
    return user;
  }
  
  public ErrMsg deleteUser(Integer id) throws Exception {
    if (Boolean.FALSE.equals(usersRepository.deleteById(id))) {
      throw new Exception("cannot update user");
    }
    return new ErrMsg("ok");
  }
}
