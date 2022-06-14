package com.pk.users.repository;

import com.pk.users.model.User;
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
public class PersistentUserRepository implements UserRepository {
  JdbcTemplate jdbcTemplate;

  @Override
  public User getById(Integer id) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"user\" where user_id = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getInt("user_id"),
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getDate("birth_date"),
                      rs.getInt("enabled"),
                      rs.getDate("creation_date"),
                      rs.getString("email"),
                      rs.getString("username"),
                      rs.getString("password"),
                      rs.getString("role"),
                      rs.getString("token")),
              id);
      if (users.size() > 1) {
        log.error("Find by username failed, more than 1 user with same id (???)");
        throw new Exception("Database tolerates duplicate id");
      } else if (!users.isEmpty()) {
        return users.get(0);
      }
      return null;
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  @Override
  public List<User> getAllUsers() {
    try {
      return jdbcTemplate.query(
          "select * from \"user\"",
          (rs, rowNum) ->
              new User(
                  rs.getInt("user_id"),
                  rs.getString("name"),
                  rs.getString("surname"),
                  rs.getDate("birth_date"),
                  rs.getInt("enabled"),
                  rs.getDate("creation_date"),
                  rs.getString("email"),
                  rs.getString("username"),
                  rs.getString("password"),
                  rs.getString("role"),
                  rs.getString("token")));
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  // TODO ignoring exceptions to return null??
  @Override
  public User getByUsername(String username) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"user\" where username = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getInt("user_id"),
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getDate("birth_date"),
                      rs.getInt("enabled"),
                      rs.getDate("creation_date"),
                      rs.getString("email"),
                      rs.getString("username"),
                      rs.getString("password"),
                      rs.getString("role"),
                      rs.getString("token")),
              username);
      if (users.size() > 1) {
        log.error("Find by username failed, more than 1 user with same id (???)");
        throw new Exception("Database tolerates duplicate id");
      } else if (!users.isEmpty()) {
        return users.get(0);
      }
      return null;
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  @Override
  public User getByEmail(String email) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"user\" where email = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getInt("user_id"),
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getDate("birth_date"),
                      rs.getInt("enabled"),
                      rs.getDate("creation_date"),
                      rs.getString("email"),
                      rs.getString("username"),
                      rs.getString("password"),
                      rs.getString("role"),
                      rs.getString("token")),
              email);
      if (users.size() > 1) {
        log.error("Find by username failed, more than 1 user with same id (???)");
        throw new Exception("Database tolerates duplicate id");
      }
      return users.get(0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  @Override
  public Integer save(User user) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "insert into \"user\" (name, surname, birth_date, enabled, creation_date,"
                        + " email, username, password, role) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setDate(3, user.getBirthDate());
            ps.setInt(4, user.getEnabled());
            ps.setDate(5, user.getCreated());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getUsername());
            ps.setString(8, user.getPassword());
            ps.setString(9, user.getAuthority());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (Integer) keyHolder.getKeys().get("order_id");
      } else {
        return keyHolder.getKey().intValue();
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public Boolean update(User user) {
    try {
      return (jdbcTemplate.update(
              "update \"user\" set name = ?, surname = ?, birth_date = ?, enabled = ?,"
                  + " creation_date = ?, username = ?, password = ?, role = ?, token = ? where"
                  + " user_id = ?",
              user.getName(),
              user.getSurname(),
              user.getBirthDate(),
              user.getEnabled(),
              user.getCreated(),
              user.getUsername(),
              user.getPassword(),
              user.getAuthority(),
              user.getToken(),
              user.getUserId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean deleteByEmail(String email) {
    try {
      return (jdbcTemplate.update("delete from \"user\" where email = ?", email) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
