package com.pk.tickets.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pk.tickets.model.Ticket;
import java.sql.Date;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class PersistentTicketRepositoryTest {
  private DataSource dataSource;
  private TicketRepository ticketRepository;

  @BeforeEach
  public void init() {
    dataSource =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("data-test.sql")
            .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    ticketRepository = new PersistentTicketRepository(jdbcTemplate);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  public void testGetById() {
    assertNotNull(ticketRepository.getById(1));
    assertNull(ticketRepository.getById(100));
  }

  @Test
  public void testSave() {
    Ticket ticket =
        new Ticket(
            0,
            3,
            3,
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis()),
            200,
            0,
            "",
            "",
            "",
            "",
            0);
    assertNull(ticketRepository.getById(4));
    assertNotEquals(0, ticketRepository.save(ticket));
    assertNotNull(ticketRepository.getById(4));
  }

  @Test
  public void testUpdate() {
    Ticket ticket = ticketRepository.getById(1);
    assertNotEquals(null, ticket);
    ticket.setPrice(1000000);
    assertTrue(ticketRepository.update(ticket));
    assertEquals(1000000, ticketRepository.getById(1).getPrice());
  }

  @Test
  public void testDelete() {
    assertNotNull(ticketRepository.getById(2));
    assertTrue(ticketRepository.delete(2));
    assertNull(ticketRepository.getById(2));
  }
}
