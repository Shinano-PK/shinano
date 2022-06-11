package com.pk.logistics.repository;

import com.pk.logistics.models.Product;
import com.pk.logistics.models.ProductRequest;
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
public class ProductPersistent implements ProductRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Product> getAll() {
    try {
      return jdbcTemplate.query(
          "SELECT * FROM PRODUCT",
          (rs, rowNum) ->
              new Product(
                  rs.getInt("ID_PRODUCT"),
                  rs.getString("NAME"),
                  rs.getString("PRODUCER"),
                  rs.getString("OUTSIDE_ID"),
                  rs.getString("DESCRIPTION"),
                  rs.getDouble("PRICE"),
                  rs.getString("UNIT"),
                  rs.getString("TYPE")));
    } catch (Exception e) {
      log.warn("Exception in get for Product", e);
      return Collections.emptyList();
    }
  }

  @Override
  public Product get(Integer id) {
    try {
      return jdbcTemplate
          .query(
              "SELECT * FROM PRODUCT WHERE ID_PRODUCT = ?",
              (rs, rowNum) ->
                  new Product(
                      rs.getInt("ID_PRODUCT"),
                      rs.getString("NAME"),
                      rs.getString("PRODUCER"),
                      rs.getString("OUTSIDE_ID"),
                      rs.getString("DESCRIPTION"),
                      rs.getDouble("PRICE"),
                      rs.getString("UNIT"),
                      rs.getString("TYPE")),
              id)
          .get(0);
    } catch (Exception e) {
      log.warn("Exception in get for Product", e);
      return null;
    }
  }

  @Override
  public Integer save(ProductRequest input) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "INSERT INTO PRODUCT (NAME, PRODUCER, OUTSIDE_ID, DESCRIPTION, PRICE, UNIT,"
                        + " TYPE) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, input.getName());
            ps.setString(2, input.getProducer());
            ps.setString(3, input.getOutsideId());
            ps.setString(4, input.getDescription());
            ps.setDouble(5, input.getPrice());
            ps.setString(6, input.getUnit());
            ps.setString(7, input.getType());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("ID_REPAIRS");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception in save for Product", e);
      return 0;
    }
  }

  @Override
  public Boolean update(Product input) {
    try {
      return (jdbcTemplate.update(
              "UPDATE PRODUCT SET NAME = ?, PRODUCER = ?, OUTSIDE_ID = ?, DESCRIPTION = ?, PRICE ="
                  + " ?, UNIT = ?, TYPE = ? WHERE ID_PRODUCT = ?",
              input.getName(),
              input.getProducer(),
              input.getOutsideId(),
              input.getDescription(),
              input.getPrice(),
              input.getUnit(),
              input.getType(),
              input.getId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception in update for Product", e);
      return false;
    }
  }

  @Override
  public Boolean delete(Integer id) {
    try {
      return (jdbcTemplate.update("DELETE FROM PRODUCT WHERE ID_PRODUCT = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception in delete for Product", e);
      return false;
    }
  }
}
