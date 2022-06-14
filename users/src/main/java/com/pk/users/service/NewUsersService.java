package com.pk.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.users.model.Email;
import com.pk.users.model.ErrMsg;
import com.pk.users.model.NewPassword;
import com.pk.users.model.Token;
import com.pk.users.model.User;
import com.pk.users.repository.TokenRepository;
import com.pk.users.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class NewUsersService {
  UserRepository usersRepository;
  TokenRepository tokenRepository;
  RestTemplate restTemplate;
  ObjectMapper objectMapper;
  PasswordEncoder passwordEncoder;

  private static final String SRC_EMAIL = "pk@noreply.pl";
  private static final String SUBJECT_NEW_ACC = "Your new account";
  private static final String SUBJECT_RESET_PASSWORD = "Password reset";
  private static final String SUBJECT_ACC_CONFIRMED = "Account confirmed";

  private static final String LINK_NEW_ACC = "http://127.0.0.1/new?token=";
  private static final String LINK_RESET_PASSWORD = "http://127.0.0.1/reset?token=";
  private static final String LINK_ACC_CONFIRMED = "http://127.0.0.1/confirmed?token=";

  private static final String DEFAULT_ROLE = "ROLE_USER";

  private static final Long TOKEN_VALID_DAYS = 3L;

  public Boolean findIfUserExists(Integer id) {
    return usersRepository.getById(id) != null;
  }

  public List<User> getAllUsers() {
    return usersRepository.getAllUsers();
  }

  public User getUserByUsername(String username) {
    return usersRepository.getByUsername(username);
  }

  public User getUserByEmail(String email) {
    return usersRepository.getByEmail(email);
  }

  public User addUser(User user) throws Exception {
    // validate input
    String emailString = user.getEmail();
    // FIXME missing last dot will be ignored
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    if (!Pattern.matches(regex, emailString)) {
      log.info("Invalid email provided: {}", emailString);
      throw new Exception("Invalid email provided");
    }

    // generate token
    Token token = new Token("", Date.valueOf(LocalDate.now().plusDays(TOKEN_VALID_DAYS)), "resetPassword");
    
    String tokenValue = tokenRepository.save(token);
    if (tokenValue == null || tokenValue.isEmpty()) {
      throw new Exception("Cannot save token to db");
    }

    // force default values 
    user.setToken(tokenValue);
    user.setEnabled(0);
    user.setAuthority(DEFAULT_ROLE);
    user.setCreated(new Date(System.currentTimeMillis()));

    // add user to db
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if (usersRepository.save(user) == 0) {
      throw new Exception("Cannot add new user");
    }

    // send email
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Email email = new Email(SRC_EMAIL, user.getEmail(), SUBJECT_NEW_ACC, LINK_NEW_ACC + user.getToken(), "newAcc");
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(email), headers);
    log.debug("Email entity: {}", entity.getBody());
    ResponseEntity<ErrMsg> response = restTemplate.exchange("http://email-service/email", HttpMethod.POST, entity, ErrMsg.class);
    log.debug("Email service response: {}", response.getBody());
    if (response.getBody() == null || response.getBody().getMessage() == null) {
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
  
  public ErrMsg deleteUser(String email) throws Exception {
    if (Boolean.FALSE.equals(usersRepository.deleteByEmail(email))) {
      throw new Exception("cannot delete user");
    }
    return new ErrMsg("ok");
  }

  public ErrMsg resetPassword(String email) throws Exception {
    // TODO more error handling
    Token token = new Token("", Date.valueOf(LocalDate.now().plusDays(TOKEN_VALID_DAYS)), "resetPassword");
    tokenRepository.save(token);
    User user = usersRepository.getByEmail(email);
    if (user == null) {
      throw new Exception("user not found");
    }
    user.setToken(token.getToken());
    if (Boolean.FALSE.equals(usersRepository.update(user))) {
      // TODO error handling
      throw new Exception("cannot update user");
    }

    // send email
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Email emailToSend = new Email(SRC_EMAIL, user.getEmail(), SUBJECT_RESET_PASSWORD, LINK_RESET_PASSWORD + user.getToken(), "newAcc");
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(emailToSend), headers);
    log.debug("Email entity: {}", entity.getBody());
    ResponseEntity<ErrMsg> response = restTemplate.exchange("http://email-service/email", HttpMethod.POST, entity, ErrMsg.class);
    log.debug("Email service response: {}", response.getBody());
    if (response.getBody() == null || response.getBody().getMessage() == null) {
      throw new Exception("body is null, email service malfunctioning");
    }
    if (response.getBody().getMessage().equalsIgnoreCase("err")) {
      throw new Exception("email service returned error message");
    }
    return new ErrMsg("ok");
  }

  public ErrMsg resetPasswordStage2(NewPassword newPassword) throws Exception {
    User user = usersRepository.getByEmail(newPassword.getLogin());
    Token token = tokenRepository.get(user.getToken());

    if (!token.getToken().equals(newPassword.getToken())) {
      // TODO invalid token provided (or mail changed)
      throw new Exception("invalid token");
    }
    user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
    if (Boolean.TRUE.equals(usersRepository.update(user))) {
      // TODO error handling
      throw new Exception("error during updating user record");
    }
    return new ErrMsg("ok");
  }
}
