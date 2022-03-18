package com.pk.email.repository;

import com.pk.email.models.EmailDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Slf4j
@AllArgsConstructor
public class PersistentEmailRepository implements EmailRepository {
  JdbcTemplate jdbcTemplate;

  @Override
  public List<EmailDB> getAll() {
    try {
      return jdbcTemplate.query(
          "select * from \"Email\"",
          (rs, rowNum) ->
              new EmailDB(
                  rs.getInt("email_id"),
                  rs.getString("source_address"),
                  rs.getString("destination_address"),
                  rs.getString("subject"),
                  rs.getString("message"),
                  rs.getString("message_type"),
                  rs.getDate("send_at")));
    } catch (Exception e) {
      log.warn("Got exception: ", e);
      return Collections.emptyList();
    }
  }

  private List<EmailDB> internalFind(String keyword, String value) {
    try {
      List<EmailDB> emails =
          jdbcTemplate.query(
              "select * from \"Email\" where " + keyword + " = ?",
              (rs, rowNum) ->
                  new EmailDB(
                      rs.getInt("email_id"),
                      rs.getString("source_address"),
                      rs.getString("destination_address"),
                      rs.getString("subject"),
                      rs.getString("message"),
                      rs.getString("message_type"),
                      rs.getDate("send_at")),
              value);
      if (emails.isEmpty()) {
        log.error("Find by username failed");
        return Collections.emptyList();
        // log.error(
        //     "More than 1 user found: "
        //         + emails.stream().map(Object::toString).collect(Collectors.joining(", ")));
      }
      return emails;
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return Collections.emptyList();
    }
  }

  @Override
  public List<EmailDB> findByEmailId(Integer id) {
    return internalFind("email_id", Integer.toString(id));
  }

  @Override
  public List<EmailDB> findBySubject(String subject) {
    return internalFind("subject", subject);
  }

  @Override
  public List<EmailDB> findByDstEmail(String dst) {
    return internalFind("destination_email", dst);
  }

  @Override
  public List<EmailDB> findBySrcEmail(String src) {
    return internalFind("source_email", src);
  }

  @Override
  public List<EmailDB> findByEmailType(String type) {
    return internalFind("message_type", type);
  }

  @Override
  public List<EmailDB> findByDate(Date date) {
    // TODO check this toString() conversion
    return internalFind("sent_at", date.toString());
  }

  @Override
  public List<EmailDB> findByMessage(String msg) {
    return internalFind("message", msg);
  }

  @Override
  public boolean deleteById(Integer id) {
    try {
      return (jdbcTemplate.update("delete from \"Email\" where email_id = ?", id) > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Integer save(EmailDB email) {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(
                    "insert into \"Email\" (source_address, destination_address, message, subject,"
                        + " sent_at, message_type) values(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email.getSrcAddress());
            ps.setString(2, email.getDstAddress());
            ps.setString(3, email.getMessage());
            ps.setString(4, email.getSubject());
            ps.setDate(5, email.getSentAt());
            ps.setString(6, email.getMessageType());
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
  public boolean update(EmailDB updatedEmail) {
    try {
      return (jdbcTemplate.update(
              "update \"Email\" set (source_address = ?, destination_address = ?, message = ?,"
                  + " subject = ?, sent_at = ?, message_type = ?) where email_id = ?",
              updatedEmail.getSrcAddress(),
              updatedEmail.getDstAddress(),
              updatedEmail.getMessage(),
              updatedEmail.getSubject(),
              updatedEmail.getSentAt(),
              updatedEmail.getMessageType(),
              updatedEmail.getEmailId())
          > 0);
    } catch (Exception e) {
      log.warn("Exception: " + e.getMessage());
      return false;
    }
  }
}
