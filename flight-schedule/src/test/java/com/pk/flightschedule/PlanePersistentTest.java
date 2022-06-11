package com.pk.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pk.flightschedule.models.Plane;
import com.pk.flightschedule.repository.PlanePersistent;
import com.pk.flightschedule.repository.PlaneRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class PlanePersistentTest {
  private PlaneRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new PlanePersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Plane plane = new Plane("TEST", 20, 30, 40, "Mr Bean");
    String id = repository.save(plane);
    assertEquals(
        repository.get(id),
        new Plane(
            id,
            plane.getFirstClassCapacity(),
            plane.getSecondClassCapacity(),
            plane.getCarryingCapacity(),
            plane.getOwner()));
  }

  @Test
  void update() {
    String id = "WHATEVER";
    Plane plane = new Plane(id, 14, 96, 324, "NOBODY");
    repository.update(plane);

    assertEquals(plane.getOwner(), repository.get(plane.getId()).getOwner());
  }

  @Test
  void delete() {
    String id = "PLANE";
    assertEquals(true, repository.delete(id));
    assertEquals(null, repository.get(id));
  }
}
