package com.pk.tickets.repository;

import com.pk.tickets.model.Ticket;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
              "select * from Ticket where ticket_id = ?",
              (rs, rowNum) ->
                  new Ticket(
                      rs.getInt("ticket_id"),
                      rs.getInt("user_id"),
                      rs.getInt("flight_id"),
                      rs.getDate("reserved_date"),
                      rs.getDate("bought_date"),
                      rs.getInt("price"),
                      rs.getInt("status"),
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getString("city"),
                      rs.getString("street"),
                      rs.getInt("building_number")),
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
  public List<Ticket> getAll() {
    try {
      List<Ticket> tickets =
          jdbcTemplate.query(
              "select * from Ticket",
              (rs, rowNum) ->
                  new Ticket(
                      rs.getInt("ticket_id"),
                      rs.getInt("user_id"),
                      rs.getInt("flight_id"),
                      rs.getDate("reserved_date"),
                      rs.getDate("bought_date"),
                      rs.getInt("price"),
                      rs.getInt("status"),
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getString("city"),
                      rs.getString("street"),
                      rs.getInt("building_number")));
      return tickets;
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
                    "insert into Ticket (user_id, flight_id, reserved_date, bought_date, price,"
                        + " status, name, surname, city, street, building_number) values(?, ?, ?,"
                        + " ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getUserId());
            ps.setInt(2, ticket.getFlightId());
            ps.setDate(3, ticket.getReservedDate());
            ps.setDate(4, ticket.getBoughtDate());
            ps.setInt(5, ticket.getPrice());
            ps.setInt(6, ticket.getStatus());
            ps.setString(7, ticket.getName());
            ps.setString(8, ticket.getSurname());
            ps.setString(9, ticket.getCity());
            ps.setString(10, ticket.getStreet());
            ps.setInt(11, ticket.getBuildingNumber());
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
              "update Ticket set user_id = ?, flight_id = ?, reserved_date = ?, bought_date = ?,"
                  + " price = ?, status = ?, name = ?, surname = ?, city = ?, street = ?,"
                  + " building_number = ? where ticket_id = ?",
              ticket.getUserId(),
              ticket.getFlightId(),
              ticket.getReservedDate(),
              ticket.getBoughtDate(),
              ticket.getPrice(),
              ticket.getStatus(),
              ticket.getName(),
              ticket.getSurname(),
              ticket.getCity(),
              ticket.getStreet(),
              ticket.getBuildingNumber(),
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
      return (jdbcTemplate.update("delete from Ticket where ticket_id = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
