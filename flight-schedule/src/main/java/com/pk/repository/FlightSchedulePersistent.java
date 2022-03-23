package com.pk.repository;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import com.pk.flightschedule.models.FlightSchedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class FlightSchedulePersistent implements FlightScheduleRepository {
  JdbcTemplate jdbcTemplate;

  @Override
  public List<FlightSchedule> getPeriod(Date start, Date end) {
      try {
        return jdbcTemplate.query("SELECXT * FROM FLIGHT_SCHEDULE WHERE SCHEDULE_START_DATE < ? AND SCHEDULE_END_DATE = ?",
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
}
