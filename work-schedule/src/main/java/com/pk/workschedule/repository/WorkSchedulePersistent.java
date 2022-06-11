package com.pk.workschedule.repository;

import com.pk.workschedule.model.WorkSchedule;
import com.pk.workschedule.model.WorkScheduleInput;
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
public class WorkSchedulePersistent implements WorkScheduleRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public WorkSchedule get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM WORK_SCHEDULE WHERE ID_WORK_SCHEDULE = ?",
              (rs, rowNum) ->
                  new WorkSchedule(
                      rs.getInt("ID_WORK_SCHEDULE"),
                      rs.getInt("ID_USER"),
                      rs.getInt("ID_TASK"),
                      rs.getInt("WEEKDAY"),
                      rs.getTime("START_TIME"),
                      rs.getTime("END_TIME"),
                      rs.getDate("SCHEDULE_START_DATE"),
                      rs.getDate("SCHEDULE_END_DATE")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return null;
    }
  }

  @Override
  public Integer save(WorkScheduleInput input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO WORK_SCHEDULE (ID_USER, ID_TASK, WEEKDAY, START_TIME,"
                        + " END_TIME, SCHEDULE_START_DATE, SCHEDULE_END_DATE) VALUES (?, ?, ?, ?,"
                        + " ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, input.getIdUser());
            ps.setInt(2, input.getIdTask());
            ps.setInt(3, input.getWeekday());
            ps.setTime(4, input.getStartTime());
            ps.setTime(5, input.getEndTime());
            ps.setDate(6, input.getScheduleStartDate());
            ps.setDate(7, input.getScheduleEndDate());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_WORK_SCHEDULE");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return -1;
    }
  }

  @Override
  public Boolean update(WorkSchedule input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE WORK_SCHEDULE SET ID_USER = ?, ID_TASK = ?, WEEKDAY = ?, START_TIME = ?,"
                  + " END_TIME = ?, SCHEDULE_START_DATE = ?, SCHEDULE_END_DATE = ? "
                  + " WHERE ID_WORK_SCHEDULE = ?",
              input.getIdUser(),
              input.getIdTask(),
              input.getWeekday(),
              input.getStartTime(),
              input.getEndTime(),
              input.getScheduleStartDate(),
              input.getScheduleEndDate(),
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
      return (jdbcTemplate.update("DELETE FROM WORK_SCHEDULE WHERE ID_WORK_SCHEDULE = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
