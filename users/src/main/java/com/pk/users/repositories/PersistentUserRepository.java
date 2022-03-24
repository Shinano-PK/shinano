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
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class PersistentUserRepository implements UsersRepository {
  JdbcTemplate jdbcTemplate;

  // TODO ignoring exceptions to return null??
  @Override
  public User getByUsername(String username) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"user\" where username = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getDate("birth_date"),
                      rs.getInt("enabled"),
                      rs.getDate("created"),
                      rs.getString("email"),
                      rs.getString("login"),
                      rs.getString("password"),
                      rs.getString("authority"),
                      rs.getString("token")),
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
  public User getByEmail(String email) {
    try {
      List<User> users =
          jdbcTemplate.query(
              "select * from \"user\" where email = ?",
              (rs, rowNum) ->
                  new User(
                      rs.getString("name"),
                      rs.getString("surname"),
                      rs.getDate("birth_date"),
                      rs.getInt("enabled"),
                      rs.getDate("created"),
                      rs.getString("email"),
                      rs.getString("login"),
                      rs.getString("password"),
                      rs.getString("authority"),
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
                    "insert into \"user\" (name, surname, birth_date, enabled, creation_date, email,"
                        + " login, password, role) values(?, ?, ?, ?, ?, ?, ?, ?,"
                        + " ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setDate(3, user.getBirthDate());
            ps.setInt(4, user.getEnabled());
            ps.setDate(5, user.getCreated());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getLogin());
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
              "update \"user\" set (name = ?, surname = ?, birth_date = ?, enabled = ?, created ="
                  + " ?, login = ?, password = ?, authority = ?, token = ?) where email = ?",
              user.getName(),
              user.getSurname(),
              user.getBirthDate(),
              user.getEnabled(),
              user.getCreated(),
              user.getLogin(),
              user.getPassword(),
              user.getAuthority(),
              user.getToken())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean deleteById(Integer id) {
    try {
      return (jdbcTemplate.update("delete from \"user\" where email = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
