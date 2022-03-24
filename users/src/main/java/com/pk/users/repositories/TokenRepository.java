package com.pk.users.repositories;

import com.pk.users.models.Token;

public interface TokenRepository {
  Token get(String token);
  
  String save(Token token);

  Boolean update(Token token);

  Boolean delete(String token);
}
