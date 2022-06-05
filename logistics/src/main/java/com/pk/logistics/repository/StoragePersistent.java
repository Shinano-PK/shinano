package com.pk.logistics.repository;

import com.pk.logistics.models.Storage;
import com.pk.logistics.models.StorageRequest;
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
public class StoragePersistent implements StorageRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Storage> getAll() {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM STORAGE",
          (rs, rowNum) ->
              new Storage(
                  rs.getInt("ID_STORAGE"),
                  rs.getString("TYPE"),
                  rs.getInt("CAPACITY"),
                  rs.getInt("AMOUNT")));
    } catch (Exception e) {
      log.warn("Exception in get for Storage", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Storage get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM STORAGE WHERE ID_STORAGE = ?",
              (rs, rowNum) ->
                  new Storage(
                      rs.getInt("ID_STORAGE"),
                      rs.getString("TYPE"),
                      rs.getInt("CAPACITY"),
                      rs.getInt("AMOUNT")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Exception in get for Storage", e);
      return null;
    }
  }

  @Override
  public Integer save(StorageRequest input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO STORAGE (TYPE, CAPACITY, AMOUNT) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, input.getType());
            ps.setInt(2, input.getCapacity());
            ps.setInt(3, input.getAmount());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_REPAIRS");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception in save for Storage", e);
      return 0;
    }
  }

  @Override
  public Boolean update(Storage input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE STORAGE SET TYPE = ?, CAPACITY = ?, AMOUNT = ? WHERE ID_STORAGE = ?",
              input.getType(),
              input.getCapacity(),
              input.getAmount(),
              input.getId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception in update for Storage", e);
      return false;
    }
  }

  @Override
  public Boolean delete(Integer id) {
    try {
      return (jdbcTemplate.update("DELETE FROM STORAGE WHERE ID_STORAGE = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception in delete for Storage", e);
      return false;
    }
  }
}
