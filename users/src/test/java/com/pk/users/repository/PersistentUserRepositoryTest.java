package com.pk.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import javax.sql.DataSource;
import com.pk.users.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class PersistentUserRepositoryTest {
  private DataSource dataSource;
  private UserRepository userRepository;

  @BeforeEach
  public void init() {
    dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("data-test.sql")
            .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    userRepository= new PersistentUserRepository(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  public void testGetById() {
    assertNotNull(userRepository.getById(1));
    assertNull(userRepository.getById(100));
  }

  @Test
  public void testGetByUsername() {
    assertNotNull(userRepository.getByUsername("test2"));
    assertNull(userRepository.getByUsername("null"));
  }

  @Test
  public void testGetByEmail() {
    assertNotNull(userRepository.getByEmail("test2@t.t"));
    assertNull(userRepository.getByEmail("null"));
  }

  @Test
  public void testSave() {
    User user = new User(0, "test", "test", Date.valueOf(LocalDate.of(2022, 10, 11)), 1, Date.valueOf(LocalDate.of(2022, 10, 11)), "test@test.test", "test", "test", "testAuth", "");
    assertNotEquals(0, userRepository.save(user));
    assertNotEquals(null, userRepository.getByEmail("test@test.test"));
  }

  @Test
  public void testUpdate() {
    User user = userRepository.getByEmail("test2@t.t");
    assertNotNull(user);
    user.setName("testName");
    assertTrue(userRepository.update(user));
    assertEquals("testName", userRepository.getByEmail("test2@t.t").getName());
  }

  @Test
  public void testDeleteByEmail() {
    assertNotNull(userRepository.getByEmail("test2@t.t"));
    assertTrue(userRepository.deleteByEmail("test2@t.t"));
    assertNull(userRepository.getByEmail("test2@t.t"));
  }

}
