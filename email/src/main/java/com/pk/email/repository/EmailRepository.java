package com.pk.email.repository;

import com.pk.email.models.EmailDB;
import java.sql.Date;
import java.util.List;

public interface EmailRepository {

  List<EmailDB> getAll();

  List<EmailDB> findByEmailId(Integer id);

  List<EmailDB> findBySubject(String subject);

  List<EmailDB> findByDstEmail(String dst);

  List<EmailDB> findBySrcEmail(String src);

  List<EmailDB> findByEmailType(String type);

  List<EmailDB> findByDate(Date date);

  List<EmailDB> findByMessage(String msg);

  boolean deleteById(Integer id);

  Integer save(EmailDB email);

  boolean update(EmailDB updatedEmail);
}
