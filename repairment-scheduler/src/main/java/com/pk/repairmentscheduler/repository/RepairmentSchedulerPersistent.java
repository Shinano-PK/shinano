package com.pk.repairmentscheduler.repository;

import com.pk.repairmentscheduler.models.RepairmentScheduler;
import com.pk.repairmentscheduler.models.RepairmentSchedulerInput;
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
public class RepairmentSchedulerPersistent implements RepairmentSchedulerRepository {
  private static final String DESCRIPTION = "DESCRIPTION";
  private static final String ID_USER = "ID_USER";
  private static final String ID_PLANE = "ID_PLANE";
  private static final String ID_REPAIRS = "ID_REPAIRS";
  private static final String GOT_EXCEPTION = "Got exception: ";
  private JdbcTemplate jdbcTemplate;

  @Override
  public RepairmentScheduler get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM REPAIRS WHERE ID_REPAIRS = ?",
              (rs, rowNum) ->
                  new RepairmentScheduler(
                      rs.getInt(ID_REPAIRS),
                      rs.getString(ID_PLANE),
                      rs.getInt(ID_USER),
                      rs.getString(DESCRIPTION)),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn(GOT_EXCEPTION, e);
      return null;
    }
  }

  @Override
  public List<RepairmentScheduler> getAll() {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM REPAIRS",
          (rs, rowNum) ->
              new RepairmentScheduler(
                  rs.getInt(ID_REPAIRS),
                  rs.getString(ID_PLANE),
                  rs.getInt(ID_USER),
                  rs.getString(DESCRIPTION)));
    } catch (Exception e) {
      log.warn(GOT_EXCEPTION, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<RepairmentScheduler> getByPlane(String idPlane) {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM REPAIRS WHERE ID_PLANE = ?",
          (rs, rowNum) ->
              new RepairmentScheduler(
                  rs.getInt(ID_REPAIRS),
                  rs.getString(ID_PLANE),
                  rs.getInt(ID_USER),
                  rs.getString(DESCRIPTION)),
          idPlane);
    } catch (Exception e) {
      log.warn(GOT_EXCEPTION, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<RepairmentScheduler> getByUser(Integer idUser) {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM REPAIRS WHERE ID_USER = ?",
          (rs, rowNum) ->
              new RepairmentScheduler(
                  rs.getInt(ID_REPAIRS),
                  rs.getString(ID_PLANE),
                  rs.getInt(ID_USER),
                  rs.getString(DESCRIPTION)),
          idUser);
    } catch (Exception e) {
      log.warn(GOT_EXCEPTION, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<RepairmentScheduler> getByPlaneAndUser(String idPlane, Integer idUser) {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM REPAIRS WHERE ID_USER = ? AND ID_PLANE = ?",
          (rs, rowNum) ->
              new RepairmentScheduler(
                  rs.getInt(ID_REPAIRS),
                  rs.getString(ID_PLANE),
                  rs.getInt(ID_USER),
                  rs.getString(DESCRIPTION)),
          idUser,
          idPlane);
    } catch (Exception e) {
      log.warn(GOT_EXCEPTION, e);
      return Collections.emptyList();
    }
  }

  @Override
  public Integer save(RepairmentSchedulerInput input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO REPAIRS (ID_PLANE, ID_USER, DESCRIPTION)" + " VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, input.getIdPlane());
            ps.setInt(2, input.getIdUser());
            ps.setString(3, input.getDescription());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get(ID_REPAIRS);
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public Boolean update(RepairmentScheduler input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE REPAIRS SET ID_PLANE = ?, ID_USER = ?, DESCRIPTION = ? WHERE ID_REPAIRS = ?",
              input.getIdPlane(),
              input.getIdUser(),
              input.getDescription(),
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
      return (jdbcTemplate.update("DELETE FROM REPAIRS WHERE ID_REPAIRS = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
