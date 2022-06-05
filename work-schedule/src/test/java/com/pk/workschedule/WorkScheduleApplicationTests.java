package com.pk.workschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.pk.workschedule.model.WorkSchedule;
import com.pk.workschedule.model.WorkScheduleInput;
import com.pk.workschedule.repository.WorkSchedulePersistent;
import com.pk.workschedule.repository.WorkScheduleRepository;
import java.sql.Date;
import java.sql.Time;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class WorkScheduleApplicationTests {
  private WorkScheduleRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new WorkSchedulePersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Integer id =
        repository.save(
            new WorkScheduleInput(
                2,
                1,
                4,
                Time.valueOf("2:50:02"),
                Time.valueOf("2:51:02"),
                Date.valueOf("2020-10-24"),
                Date.valueOf("2020-11-10")));
    assertEquals(3, id);
    assertEquals(
        repository.get(id),
        new WorkSchedule(
            id,
            2,
            1,
            4,
            Time.valueOf("2:50:02"),
            Time.valueOf("2:51:02"),
            Date.valueOf("2020-10-24"),
            Date.valueOf("2020-11-10")));
  }

  @Test
  void update() {
    Integer id = 1;
    WorkSchedule workSchedule = repository.get(id);
    Boolean result =
        repository.update(
            new WorkSchedule(
                id,
                workSchedule.getIdUser(),
                workSchedule.getIdTask(),
                1,
                workSchedule.getStartTime(),
                workSchedule.getEndTime(),
                workSchedule.getScheduleStartDate(),
                workSchedule.getScheduleEndDate()));
    WorkSchedule check = repository.get(id);

    assertEquals(Boolean.TRUE, result);
    assertNotEquals(workSchedule.getWeekday(), check.getWeekday());
    assertEquals(workSchedule.getScheduleEndDate(), check.getScheduleEndDate());
  }

  @Test
  void delete() {
    Integer id = 2;
    Boolean result = repository.delete(id);
    assertEquals(Boolean.TRUE, result);
    assertEquals(Boolean.FALSE, repository.delete(id));
  }
}
