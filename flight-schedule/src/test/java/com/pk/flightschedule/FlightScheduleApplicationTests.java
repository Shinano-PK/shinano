package com.pk.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;
import com.pk.flightschedule.repository.FlightSchedulePersistent;
import com.pk.flightschedule.repository.FlightScheduleRepository;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class FlightScheduleApplicationTests {
  private FlightScheduleRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new FlightSchedulePersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Integer id =
        repository.save(
            new FlightScheduleInput(
                2,
                4,
                Time.valueOf("2:50:02"),
                Time.valueOf("2:51:02"),
                Date.valueOf("2020-10-24"),
                Date.valueOf("2020-11-10"),
                "warsaw",
                "cracow",
                "passenger"));
    assertEquals(3, id);
    assertEquals(
        repository.get(id),
        new FlightSchedule(
            id,
            2,
            4,
            Time.valueOf("2:50:02"),
            Time.valueOf("2:51:02"),
            Date.valueOf("2020-10-24"),
            Date.valueOf("2020-11-10"),
            "warsaw",
            "cracow",
            "passenger"));
  }

  @Test
  void update() {
    Integer id = 1;
    repository.update(
        new FlightSchedule(
            id,
            2,
            4,
            Time.valueOf("2:50:02"),
            Time.valueOf("2:51:02"),
            Date.valueOf("2020-10-24"),
            Date.valueOf("2020-11-10"),
            "warsaw",
            "cracow",
            "passenger"));
    assertEquals(
        repository.get(id),
        new FlightSchedule(
            id,
            2,
            4,
            Time.valueOf("2:50:02"),
            Time.valueOf("2:51:02"),
            Date.valueOf("2020-10-24"),
            Date.valueOf("2020-11-10"),
            "warsaw",
            "cracow",
            "passenger"));
  }

  @Test
  void delete() {
    Integer id = 3;
    repository.delete(id);
    assertEquals(null, repository.get(id));
  }

  @Test
  void getPeriod2() {
    List<FlightSchedule> schedules =
        repository.getPeriod(Date.valueOf("2021-10-24"), Date.valueOf("2021-11-09"));
    assertEquals(2, schedules.size());
  }

  @Test
  void getPeriod1() {
    List<FlightSchedule> schedules =
        repository.getPeriod(Date.valueOf("2021-10-26"), Date.valueOf("2021-11-09"));
    assertEquals(1, schedules.size());
  }

  @Test
  void getPeriod0() {
    List<FlightSchedule> schedules =
        repository.getPeriod(Date.valueOf("2021-10-26"), Date.valueOf("2021-10-27"));
    assertEquals(0, schedules.size());
  }
}
