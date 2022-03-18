package com.pk.internal.service;

import java.util.Arrays;

import com.pk.internal.models.Authority;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// FIXME temp solution
@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  // UserRepository userRepository;
  // AuthorityRepository authRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // com.pk.gsmanager.models.User user = userRepository.findByUsername(username);

    // if (user == null) {
    //   throw new UsernameNotFoundException("User not found");
    // }
    // Authority auth = authRepository.findByUsername(username);
    // log.info("Got user: " + user.toString());
    // log.info("Got auth: " + auth.toString());
    // return new User(user.getUsername(), user.getPassword(), Arrays.asList(auth));
    return new User("test", "test", Arrays.asList(new Authority("test", "ROLE_ADMIN")));
  }
}
