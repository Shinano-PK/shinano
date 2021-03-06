package com.pk.tickets.controllers;

import com.pk.tickets.model.ErrMsg;
import com.pk.tickets.model.Ticket;
import com.pk.tickets.service.TicketsService;

import java.util.List;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TicketController {
  TicketsService ticketsService;

  @GetMapping("/ticket/{id}")
  public Ticket getTicketDetails(@PathVariable Integer id) throws Exception {
    log.trace("getTicketDetails() called");
    return ticketsService.getTicketDetails(id);
  }

  @GetMapping("/ticket")
  public List<Ticket> getTickets() {
    log.trace("getTickets() called");
    return ticketsService.getTickets();
  }

  @PostMapping("/ticket")
  public Ticket addNewTicket(@RequestBody @Valid Ticket ticket) throws Exception {
    log.trace("addNewTicket() called");
    return ticketsService.addNewTicket(ticket);
  }

  @PutMapping("/ticket")
  public Ticket updateTicket(@RequestBody @Valid Ticket ticket) throws Exception {
    log.trace("updateTicket() called");
    return ticketsService.updateTicket(ticket);
  }

  @DeleteMapping("/ticket")
  public ErrMsg deleteTicket(@RequestParam Integer id) throws Exception {
    log.trace("deleteTicket() called");
    return ticketsService.deleteTicket(id);
  }
}
