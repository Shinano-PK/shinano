package com.pk.flightschedule.repository;

import com.pk.flightschedule.models.Plane;
import com.pk.flightschedule.models.PlaneInput;
import java.sql.PreparedStatement;
import java.sql.Statement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@AllArgsConstructor
public class PlanePersistent implements PlaneRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public Plane get(String id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM PLANE WHERE ID_PLANE = ?",
              (rs, rowNum) ->
                  new Plane(
                      rs.getString("ID_PLANE"),
                      rs.getInt("FIRST_CLASS_CAPACITY"),
                      rs.getInt("SECOND_CLASS_CAPACITY"),
                      rs.getInt("CARRYING_CAPACITY"),
                      rs.getString("OWNER")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return null;
    }
  }

  @Override
  public Integer save(PlaneInput input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO PLANE (FIRST_CLASS_CAPACITY, SECOND_CLASS_CAPACITY,"
                        + " CARRYING_CAPACITY, OWNER) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, input.getFirstClassCapacity());
            ps.setInt(2, input.getSecondClassCapacity());
            ps.setInt(3, input.getCarryingCapacity());
            ps.setString(4, input.getOwner());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_PLANE");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public Boolean update(Plane input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE PLANE SET FIRST_CLASS_CAPACITY = ?, SECOND_CLASS_CAPACITY = ?,"
                  + " CARRYING_CAPACITY = ?, OWNER = ? WHERE ID_PLANE = ?",
              input.getFirstClassCapacity(),
              input.getSecondClassCapacity(),
              input.getCarryingCapacity(),
              input.getOwner(),
              input.getId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean delete(String id) {
    try {
      return (jdbcTemplate.update("DELETE FROM PLANE WHERE ID_PLANE = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
