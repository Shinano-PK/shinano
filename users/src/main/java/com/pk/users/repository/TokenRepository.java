package com.pk.users.repository;

import com.pk.users.model.Token;

public interface TokenRepository {
  Token get(String token);
  
  String save(Token token);

  Boolean update(Token token);

  Boolean delete(String token);
}
