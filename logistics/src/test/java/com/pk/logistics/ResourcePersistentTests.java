package com.pk.logistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pk.logistics.models.Resource;
import com.pk.logistics.models.ResourceRequest;
import com.pk.logistics.repository.ResourcePersistent;
import com.pk.logistics.repository.ResourceRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class ResourcePersistentTests {
  private ResourceRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new ResourcePersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Integer id = repository.save(new ResourceRequest(1, 2, 10));
    assertEquals(repository.getAll().size(), id);
    assertEquals(repository.get(id), new Resource(id, 1, 2, 10));
  }

  @Test
  void update() {
    Integer id = 2;
    Resource assertion = repository.get(id);
    Boolean result =
        repository.update(
            new Resource(
                id,
                assertion.getIdProduct(),
                assertion.getIdStorage(),
                15));
    Resource check = repository.get(id);

    assertEquals(Boolean.TRUE, result);
    assertNotEquals(assertion.getQuantity(), check.getQuantity());
    assertEquals(assertion.getIdProduct(), check.getIdProduct());

    result =
        repository.update(
            new Resource(
                -1,
                assertion.getIdProduct(),
                assertion.getIdStorage(),
                20));
    assertEquals(Boolean.FALSE, result);
  }

  @Test
  void delete() {
    Boolean result = repository.delete(repository.getAll().size());

    assertEquals(Boolean.TRUE, result);

    result = repository.delete(0);
    assertEquals(Boolean.FALSE, result);
  }
}
