package com.pk.users.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import javax.sql.DataSource;
import com.pk.users.model.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class PersistentTokenRepositoryTest {
  private DataSource dataSource;
  private TokenRepository tokenRepository;

  @BeforeEach
  public void init() {
    dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("data-test.sql")
            .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    tokenRepository = new PersistentTokenRepository(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  public void testGet() {
    assertNotNull(tokenRepository.get("token1"));
    assertNull(tokenRepository.get("token100"));
  }

  @Test
  public void testSave() {
    Token token = new Token("tokenX", Date.valueOf(LocalDate.of(2022, 10, 11)), "testType");
    String newToken = tokenRepository.save(token);
    assertNotNull(newToken);
    assertNotEquals(null, tokenRepository.get(newToken));
  }

  @Test
  public void testUpdate() {
    Token token = tokenRepository.get("token1");
    token.setType("testType");
    assertTrue(tokenRepository.update(token));
    assertEquals("testType", tokenRepository.get("token1").getType());
  }

  @Test
  public void testDelete() {
    assertNotNull(tokenRepository.get("token1"));
    assertTrue(tokenRepository.delete("token1"));
    assertNull(tokenRepository.get("token1"));
  }
}
