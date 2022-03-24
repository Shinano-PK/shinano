package com.pk.users.repositories;

import com.pk.users.models.Token;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class PersistentTokenRepository implements TokenRepository {
  JdbcTemplate jdbcTemplate;

  @Override
  public Token get(String token) {
    // TODO null or exception
    if (token == null) {
      return null;
    }
    try {
      List<Token> tokens =
          jdbcTemplate.query(
              "select * from Token where token = ?",
              (rs, rowNum) ->
                  new Token(rs.getString("token"), rs.getString("type"), rs.getDate("valid_until")),
              token);
      if (tokens.size() > 1) {
        log.error("Find by token failed, more than 1 token with same key (???)");
        throw new Exception("Database tolerates duplicate id");
      } else if (!tokens.isEmpty()) {
        return tokens.get(0);
      }
      return null;
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return null;
    }
  }

  @Override
  public String save(Token token) {
    try {
      token.setToken(generateUuid());
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "insert into Token (token, type, valid_until) values(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, token.getToken());
            ps.setString(2, token.getType());
            ps.setDate(3, token.getValidUntil());
            return ps;
          },
          keyHolder);
      if (keyHolder.getKeys().size() > 1) {
        return (String) keyHolder.getKeys().get("token");
      } else {
        return keyHolder.getKeyAs(String.class);
      }
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return "";
    }
  }

  private String generateUuid() {
    UUID uuid = UUID.randomUUID();
    while (get(uuid.toString()) != null) {
      uuid = UUID.randomUUID();
    }
    return uuid.toString();
  }

  @Override
  public Boolean update(Token token) {
    try {
      return (jdbcTemplate.update(
              "update token set (token = ?, type = ?, valid_until = ?) where email = ?",
              token.getToken(),
              token.getType(),
              token.getValidUntil())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean delete(String token) {
    try {
      return (jdbcTemplate.update("delete from token where token = ?", token) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
