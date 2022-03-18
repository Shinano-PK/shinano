package com.pk.tickets.repository;

import com.pk.tickets.models.Ticket;

public interface TicketRepository {
  Ticket getById(Integer id);

  Integer save(Ticket ticket);

  boolean update(Ticket ticket);

  boolean delete(Integer id);
}
