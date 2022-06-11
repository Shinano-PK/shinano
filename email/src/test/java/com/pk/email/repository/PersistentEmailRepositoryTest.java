package com.pk.email.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.time.LocalDate;
import javax.sql.DataSource;
import com.pk.email.model.EmailDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class PersistentEmailRepositoryTest {
  private DataSource dataSource;
  private EmailRepository emailRepository;

  @BeforeEach
  public void init() {
    dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("data-test.sql")
            .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    emailRepository = new PersistentEmailRepository(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  public void testGetAll() {
    assertEquals(3, emailRepository.getAll().size());
  }
  
  @Test
  public void testFindByEmailId() {
    assertEquals(1, emailRepository.findByEmailId(1).size());
    assertEquals(0, emailRepository.findByEmailId(100).size());
  }

  @Test
  public void testFindBySubject() {
    assertEquals(1, emailRepository.findBySubject("Test subject 1").size());
    assertEquals(0, emailRepository.findBySubject("Test missing subject").size());
  }

  @Test
  public void testFindByDstEmail() {
    assertEquals(1, emailRepository.findByDstEmail("t1@t.t").size());
    assertEquals(0, emailRepository.findByDstEmail("null@t.t").size());
  }

  @Test
  public void testFindBySrcEmail() {
    assertEquals(3, emailRepository.findBySrcEmail("pkwieikproject@gmail.com").size());
    assertEquals(0, emailRepository.findBySrcEmail("null").size());
  }

  @Test
  public void findByEmailType() {
    assertEquals(1, emailRepository.findByEmailType("newAcc").size());
    assertEquals(0, emailRepository.findByEmailType("null").size());
  }

  @Test
  public void findByDate() {
    assertEquals(1, emailRepository.findByDate(Date.valueOf(LocalDate.of(2022, 10, 11))).size());
    assertEquals(0, emailRepository.findByDate(new Date(System.currentTimeMillis())).size());
  }

  @Test
  public void findByMessage() {
    assertEquals(1, emailRepository.findByMessage("Test message 3").size());
    assertEquals(0, emailRepository.findByMessage("null").size());
  }

  @Test
  public void testDeleteById() {
    assertEquals(1, emailRepository.findByEmailId(1).size());
    assertEquals(true, emailRepository.deleteById(1));
    assertEquals(0, emailRepository.findByEmailId(1).size());

    assertEquals(0, emailRepository.findByEmailId(100).size());
    assertEquals(false, emailRepository.deleteById(100));
    assertEquals(0, emailRepository.findByEmailId(100).size());
  }
  
  @Test
  public void testSave() {
    EmailDB email = new EmailDB(0, "test@test.test", "test2@test2.test2", "test", "test", "test", new Date(System.currentTimeMillis()));
    assertEquals(3, emailRepository.getAll().size());
    assertNotEquals(0, emailRepository.save(email));
    assertEquals(4, emailRepository.getAll().size());
  }
  
  @Test
  public void testUpdate() {
    assertEquals(1, emailRepository.findByEmailId(1).size());
    EmailDB email = emailRepository.findByEmailId(1).get(0);
    email.setMessage("testUpdate");
    assertEquals(true, emailRepository.update(email));
    emailRepository.findByEmailId(1);
    assertEquals("testUpdate", emailRepository.findByEmailId(1).get(0).getMessage());
  }
}
