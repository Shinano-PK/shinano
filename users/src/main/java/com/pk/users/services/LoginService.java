// package com.pk.users.services;

// import com.pk.users.models.Login;
// import com.pk.users.models.User;
// import com.pk.users.repositories.UsersRepository;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Service
// @AllArgsConstructor
// public class LoginService {
//   PasswordEncoder passwordEncoder;
//   UsersRepository usersRepository;

//   public User login(Login loginData) throws Exception {
//     User userFromDb = usersRepository.getByEmail(loginData.getLogin());
//     if (userFromDb == null) {
//       log.debug("User {} not found in db", loginData.getLogin());
//       throw new Exception("Invalid email provided");
//     }
//     if (userFromDb.getEnabled() != 1) {
//       log.debug("User is not enabled");
//       throw new Exception("Account is disabled");
//     }
//     if (!passwordEncoder.matches(loginData.getPassword(), userFromDb.getPassword())) {
//       log.debug("Passwords mismatch");
//       throw new Exception("Invalid password");
//     }
//     return userFromDb;
//   }
// }
