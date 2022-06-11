package com.pk.logistics.repository;

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

import com.pk.logistics.model.Resource;
import com.pk.logistics.model.ResourceRequest;

@Repository
@Slf4j
@AllArgsConstructor
public class ResourcePersistent implements ResourceRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Resource> getAll() {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM RESOURCE",
          (rs, rowNum) ->
              new Resource(
                  rs.getInt("ID_RESOURCE"),
                  rs.getInt("ID_PRODUCT"),
                  rs.getInt("ID_STORAGE"),
                  rs.getInt("QUANTITY")));
    } catch (Exception e) {
      log.warn("Exception in get for Resource {}", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Resource get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM RESOURCE WHERE ID_RESOURCE = ?",
              (rs, rowNum) ->
                  new Resource(
                      rs.getInt("ID_RESOURCE"),
                      rs.getInt("ID_PRODUCT"),
                      rs.getInt("ID_STORAGE"),
                      rs.getInt("QUANTITY")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Exception in get for Resource {}", e);
      return null;
    }
  }

  @Override
  public Integer save(ResourceRequest input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO RESOURCE (ID_PRODUCT, ID_STORAGE, QUANTITY) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, input.getIdProduct());
            ps.setInt(2, input.getIdStorage());
            ps.setInt(3, input.getQuantity());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_REPAIRS");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception in save for Resource {}", e);
      return 0;
    }
  }

  @Override
  public Boolean update(Resource input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE RESOURCE SET ID_PRODUCT = ?, ID_STORAGE = ?, QUANTITY = ? WHERE ID_RESOURCE ="
                  + " ?",
              input.getIdProduct(),
              input.getIdStorage(),
              input.getQuantity(),
              input.getId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception in update for Resource {}", e);
      return false;
    }
  }

  @Override
  public Boolean delete(Integer id) {
    try {
      return (jdbcTemplate.update("DELETE FROM RESOURCE WHERE ID_RESOURCE = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception in delete for Resource {}", e);
      return false;
    }
  }
}
