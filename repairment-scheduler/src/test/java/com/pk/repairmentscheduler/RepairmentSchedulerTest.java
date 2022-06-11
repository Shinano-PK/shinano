package com.pk.repairmentscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pk.repairmentscheduler.models.RepairmentScheduler;
import com.pk.repairmentscheduler.models.RepairmentSchedulerInput;
import com.pk.repairmentscheduler.repository.RepairmentSchedulerPersistent;
import com.pk.repairmentscheduler.repository.RepairmentSchedulerRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class RepairmentSchedulerTest {
  private RepairmentSchedulerRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new RepairmentSchedulerPersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    RepairmentSchedulerInput repairmentScheduler =
        new RepairmentSchedulerInput("WHATEVER", 1, "THERE IS DESCRIPTION");
    Integer id = repository.save(repairmentScheduler);
    assertEquals(repository.getAll().size(), id);
    assertEquals(
        repository.get(id),
        new RepairmentScheduler(
            id,
            repairmentScheduler.getIdPlane(),
            repairmentScheduler.getIdUser(),
            repairmentScheduler.getDescription()));
  }

  @Test
  void update() {
    Integer id = 1;
    RepairmentScheduler beforeUpdate = repository.get(id);
    RepairmentScheduler repairmentScheduler =
        new RepairmentScheduler(id, "SOMETHING", 2, "THIS IS DESCRIPTION");
    Boolean result = repository.update(repairmentScheduler);

    assertEquals(Boolean.TRUE, result);
    assertNotEquals(beforeUpdate.getIdPlane(), repairmentScheduler.getIdPlane());
    assertNotEquals(beforeUpdate.getDescription(), repairmentScheduler.getDescription());
  }

  @Test
  void delete() {
    Boolean result = repository.delete(repository.getAll().size());

    assertEquals(Boolean.TRUE, result);

    result = repository.delete(0);
    assertEquals(Boolean.FALSE, result);
  }
}
