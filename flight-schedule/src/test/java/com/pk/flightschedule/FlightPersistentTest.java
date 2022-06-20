package com.pk.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pk.flightschedule.models.Flight;
import com.pk.flightschedule.repository.FlightPersistent;
import com.pk.flightschedule.repository.FlightRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
class FlightPersistentTest {
  private FlightRepository repository;
  private DataSource dataSource;

  @BeforeEach
  void initialize() {
    this.dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("test-data.sql")
            .build();
    this.repository = new FlightPersistent(new JdbcTemplate(dataSource));
  }

  @AfterEach
  void destroy() {
    new JdbcTemplate(dataSource).execute("DROP ALL OBJECTS");
  }

  @Test
  void save() {
    Flight flight = new Flight(null, "PLANE", 1, 10, "NOT READY", "A2");
    Integer id = repository.save(flight);
    assertEquals(
        repository.get(id),
        new Flight(id, flight.getIdPlane(), flight.getIdFlightSchedule(), flight.getDelay(), flight.getStatus(), flight.getRunway()));
  }

  @Test
  void update() {
    Flight flight = new Flight(2, "CASTLE", 2, 24, "READY", "B3");
    repository.update(flight);

    assertEquals(flight.getIdPlane(), repository.get(flight.getId()).getIdPlane());
  }

  @Test
  void delete() {
    Integer id = 2;
    assertEquals(true, repository.delete(id));
    assertEquals(null, repository.get(id));
  }
}
