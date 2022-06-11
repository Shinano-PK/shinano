package com.pk.logistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pk.logistics.model.Storage;
import com.pk.logistics.model.StorageRequest;
import com.pk.logistics.repository.StoragePersistent;
import com.pk.logistics.repository.StorageRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class StoragePersistentTests {
  private StorageRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new StoragePersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Integer id = repository.save(new StorageRequest("SOLID", 20, 0));
    assertEquals(repository.getAll().size(), id);
    assertEquals(repository.get(id), new Storage(id, "SOLID", 20, 0));
  }

  @Test
  void update() {
    Integer id = 2;
    Storage assertion = repository.get(id);
    Boolean result =
        repository.update(new Storage(id, "TYPE", assertion.getCapacity(), assertion.getAmount()));
    Storage check = repository.get(id);

    assertEquals(Boolean.TRUE, result);
    assertNotEquals(assertion.getType(), check.getType());
    assertEquals(assertion.getAmount(), check.getAmount());

    result =
        repository.update(new Storage(-1, "TYPE", assertion.getCapacity(), assertion.getAmount()));
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
