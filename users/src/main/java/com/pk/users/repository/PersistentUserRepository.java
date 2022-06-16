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
      if (users.isEmpty()) {
        return null;
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
                    "insert into \"user\" (enabled, creation_date,"
                        + " email, username, password, role) values(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getEnabled());
            ps.setDate(2, user.getCreated());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getAuthority());
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
      if (user.getToken() == null || user.getToken().isEmpty()) {
        return (jdbcTemplate.update(
                "update \"user\" set enabled = ?,"
                    + " creation_date = ?, username = ?, email = ?, password = ?, role = ? where"
                    + " user_id = ?",
                user.getEnabled(),
                user.getCreated(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthority(),
                user.getUserId())
            > 0);
      } else {
        return (jdbcTemplate.update(
                "update \"user\" set enabled = ?,"
                    + " creation_date = ?, username = ?, email = ?, password = ?, role = ?, token = ? where"
                    + " user_id = ?",
                user.getEnabled(),
                user.getCreated(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthority(),
                user.getToken(),
                user.getUserId())
            > 0);
      }
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
