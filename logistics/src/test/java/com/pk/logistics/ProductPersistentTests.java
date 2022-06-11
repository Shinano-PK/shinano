package com.pk.logistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pk.logistics.model.Product;
import com.pk.logistics.model.ProductRequest;
import com.pk.logistics.repository.ProductPersistent;
import com.pk.logistics.repository.ProductRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class ProductPersistentTests {
  private ProductRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new ProductPersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Integer id =
        repository.save(
            new ProductRequest(
                "BUTTERFLY KNIFE",
                "KNIFES",
                "YES",
                "KNIFE THAT WILL CUT YOU DOWN",
                120.0,
                "PIECE",
                "SOLID"));
    assertEquals(repository.getAll().size(), id);
    assertEquals(
        repository.get(id),
        new Product(
            id,
            "BUTTERFLY KNIFE",
            "KNIFES",
            "YES",
            "KNIFE THAT WILL CUT YOU DOWN",
            120.0,
            "PIECE",
            "SOLID"));
  }

  @Test
  void update() {
    Integer id = 2;
    Product assertion = repository.get(id);
    Boolean result =
        repository.update(
            new Product(
                id,
                "ONLY KNIFE",
                assertion.getProducer(),
                assertion.getOutsideId(),
                assertion.getDescription(),
                assertion.getPrice(),
                assertion.getUnit(),
                assertion.getType()));
    Product check = repository.get(id);

    assertEquals(Boolean.TRUE, result);
    assertNotEquals(assertion.getName(), check.getName());
    assertEquals(assertion.getProducer(), check.getProducer());

    result =
        repository.update(
            new Product(
                -1,
                "ONLY KNIFE",
                assertion.getProducer(),
                assertion.getOutsideId(),
                assertion.getDescription(),
                assertion.getPrice(),
                assertion.getUnit(),
                assertion.getType()));
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
