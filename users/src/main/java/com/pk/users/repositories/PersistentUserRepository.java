package com.pk.users.repositories;

import com.pk.users.models.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Slf4j
@AllArgsConstructor
public class PersistentUserRepository implements UsersRepository {
  JdbcTemplate jdbcTemplate;

  // TODO ignoring exceptions to return null??
  @Override
  public User getByUsername(String username) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"User\" where username = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getString("username"),
                      rs.getString("email"),
                      rs.getString("password"),
                      rs.getInt("enabled"),
                      rs.getDate("created"),
                      rs.getString("authority")),
              username);
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
                    "insert into \"User\" (username, email, password, enabled,"
                        + " created, authority) values(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getEnabled());
            ps.setDate(5, user.getCreated());
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
      return (jdbcTemplate.update(
              "update \"User\" set (username = ?, email = ?, password = ?, enabled = ?,"
                  + " created = ?, authority = ?) where email = ?",
              user.getUsername(),
              user.getEmail(),
              user.getPassword(),
              user.getEnabled(),
              user.getCreated(),
              user.getAuthority(),
              user.getEmail())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean deleteById(Integer id) {
    try {
      return (jdbcTemplate.update("delete from \"User\" where email = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
