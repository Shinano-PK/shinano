package com.pk.flightschedule.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

import com.pk.flightschedule.models.FlightSchedule;
import com.pk.flightschedule.models.FlightScheduleInput;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class FlightSchedulePersistent implements FlightScheduleRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<FlightSchedule> getPeriod(Date start, Date end) {
    try {
      return jdbcTemplate.query(
          "SELECXT * FROM FLIGHT_SCHEDULE WHERE SCHEDULE_START_DATE < ? AND SCHEDULE_END_DATE = ?", // TODO This should have different query
          (rs, rowNum) -> new FlightSchedule(
              rs.getInt("ID_FLIGHT_SCHEDULE"),
              rs.getInt("START_WEEKDAY"),
              rs.getInt("END_WEEKDAY"),
              rs.getTime("START_TIME"),
              rs.getTime("END_TIME"),
              rs.getDate("SCHEDULE_START_DATE"),
              rs.getDate("SCHEDULE_END_DATE"),
              rs.getString("DESTINATION"),
              rs.getString("FROM"),
              rs.getString("KIND")),
          start,
          end);
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Integer save(FlightScheduleInput input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO FLIGHT_SCHEDULE (START_WEEKDAY, END_WEEKDAY, " +
                " START_TIME, END_TIME, SCHEDULE_START_DATE, SCHEDULE_END_DATE, DESTINATION, \"FORM\", KIND)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, input.getStartWeekday());
        ps.setInt(2, input.getEndWeekday());
        ps.setTime(3, input.getStartTime());
        ps.setTime(4, input.getEndTime());
        ps.setDate(5, input.getScheduleStartDate());
        ps.setDate(6, input.getScheduleEndDate());
        ps.setString(7, input.getDestination());
        ps.setString(8, input.getFrom());
        ps.setString(9, input.getKind());
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
}
