package com.pk.flightschedule.repository;

import com.pk.flightschedule.models.Flight;
import com.pk.flightschedule.models.FlightInput;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class FlightPersistent implements FlightRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public Flight get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM FLIGHT WHERE ID_FLIGHT = ?",
              (rs, rowNum) ->
                  new Flight(
                      rs.getInt("ID_FLIGHT"),
                      rs.getString("ID_PLANE"),
                      rs.getInt("ID_FLIGHT_SCHEDULE"),
                      rs.getInt("DELAY"),
                      rs.getString("STATUS"),
                      rs.getString("RUNWAY")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return null;
    }
  }

  @Override
  public List<Flight> getAll() {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM FLIGHT",
          (rs, rowNum) ->
              new Flight(
                  rs.getInt("ID_FLIGHT"),
                  rs.getString("ID_PLANE"),
                  rs.getInt("ID_FLIGHT_SCHEDULE"),
                  rs.getInt("DELAY"),
                  rs.getString("STATUS"),
                  rs.getString("RUNWAY")));
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<Flight> getByFlightSchedule(Integer id) {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM FLIGHT WHERE ID_FLIGHT_SCHEDULE = ?",
          (rs, rowNum) ->
              new Flight(
                  rs.getInt("ID_FLIGHT"),
                  rs.getString("ID_PLANE"),
                  rs.getInt("ID_FLIGHT_SCHEDULE"),
                  rs.getInt("DELAY"),
                  rs.getString("STATUS"),
                  rs.getString("RUNWAY")),
          id);
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Integer save(FlightInput input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO FLIGHT (ID_PLANE, ID_FLIGHT_SCHEDULE, DELAY, STATUS, RUNWAY)"
                        + " VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, input.getIdPlane());
            ps.setInt(2, input.getIdFlightSchedule());
            ps.setInt(3, input.getDelay());
            ps.setString(4, input.getStatus());
            ps.setString(5, input.getRunway());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_FLIGHT_SCHEDULE");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public Boolean update(Flight input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE FLIGHT SET ID_PLANE = ?, ID_FLIGHT_SCHEDULE = ?, DELAY = ?, STATUS = ?, RUNWAY = ? WHERE ID_FLIGHT ="
                  + " ?",
              input.getIdPlane(),
              input.getIdFlightSchedule(),
              input.getDelay(),
              input.getStatus(),
              input.getRunway(),
              input.getId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean delete(Integer id) {
    try {
      return (jdbcTemplate.update("DELETE FROM FLIGHT WHERE ID_FLIGHT = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
