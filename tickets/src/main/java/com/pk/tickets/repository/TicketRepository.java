package com.pk.tickets.repository;

import java.util.List;

import com.pk.tickets.model.Ticket;

public interface TicketRepository {
  Ticket getById(Integer id);

  List<Ticket> getAll();

  Integer save(Ticket ticket);

  boolean update(Ticket ticket);

  boolean delete(Integer id);
}
