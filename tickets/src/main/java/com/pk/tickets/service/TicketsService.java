package com.pk.tickets.service;

import com.pk.tickets.model.ErrMsg;
import com.pk.tickets.model.Ticket;
import com.pk.tickets.repository.TicketRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TicketsService {
  TicketRepository ticketRepository;

  public Ticket getTicketDetails(Integer id) throws Exception {
    log.trace("getTicketDetails() called");
    Ticket ticket = ticketRepository.getById(id);
    if (ticket == null) {
      throw new Exception("Ticket not found");
    }
    return ticket;
  }

  public List<Ticket> getTickets() {
    return ticketRepository.getAll();
  }

  public Ticket addNewTicket(Ticket ticket) throws Exception {
    log.trace("addNewTicket() called");
    Integer id = ticketRepository.save(ticket);
    if (id == 0) {
      throw new Exception("Cannot create ticket");
    }
    return ticket;
  }

  public Ticket updateTicket(Ticket ticket) throws Exception {
    log.trace("updateTicket() called");
    if (!ticketRepository.update(ticket)) {
      throw new Exception("Cannot update ticket");
    }
    return ticket;
  }

  public ErrMsg deleteTicket(Integer id) throws Exception {
    log.trace("deleteTicket() called");
    if (!ticketRepository.delete(id)) {
      throw new Exception("Cannot update ticket");
    }
    return new ErrMsg("Ok");
  }
}
