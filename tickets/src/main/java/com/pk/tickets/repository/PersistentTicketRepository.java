package com.pk.tickets.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import com.pk.tickets.models.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class PersistentTicketRepository implements TicketRepository {

  JdbcTemplate jdbcTemplate;

  @Override
  public Ticket getById(Integer id) {
    try {
      List<Ticket> tickets =
          jdbcTemplate.query(
              "select * from \"Ticket\" where ticket_id = ?",
              (rs, rowNum) ->
                  new Ticket(
                      rs.getInt("ticket_id"),
                      rs.getInt("user_id"),
                      rs.getInt("flight_id"),
                      rs.getDate("reserved_date"),
                      rs.getDate("bought_date"),
                      rs.getInt("price"),
                      rs.getInt("status")),
              id);
      if (tickets.isEmpty()) {
        log.error("Find by id failed");
        return null;
      }
      return tickets.get(0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  @Override
  public Integer save(Ticket ticket) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "insert into \"Ticket\" (ticket_id, user_id, flight_id, reserved_date, bought_date, price, status) values(?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getTicketId());
            ps.setInt(2, ticket.getUserId());
            ps.setInt(3, ticket.getFlightId());
            ps.setDate(4, ticket.getReservedDate());
            ps.setDate(5, ticket.getBoughtDate());
            ps.setInt(6, ticket.getPrice());
            ps.setInt(7, ticket.getStatus());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ticket_id");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public boolean update(Ticket ticket) {
    try {
      return (jdbcTemplate.update(
              "update \"Ticket\" set (user_id = ?, flight_id = ?,"
                  + " reserved_date = ?, bought_date = ?, price_id = ? where ticket_id = ?",
              ticket.getUserId(),
              ticket.getFlightId(),
              ticket.getReservedDate(),
              ticket.getBoughtDate(),
              ticket.getPrice(),
              ticket.getStatus(),
              ticket.getTicketId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean delete(Integer id) {
    try {
      return (jdbcTemplate.update("delete from \"Ticket\" where ticket_id = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
