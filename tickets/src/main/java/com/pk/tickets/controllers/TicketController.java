package com.pk.tickets.controllers;

import javax.validation.Valid;

import com.pk.tickets.models.ErrMsg;
import com.pk.tickets.models.Ticket;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
  
  // @GetMapping("/ticker")
  // public Ticker getAllTickers() {
  //   ;
  // }

    @GetMapping("/ticket")
    public Ticket getTickerDetails(@RequestParam Integer id) {
      return null;
    }

    @PostMapping("/ticket")
    public Ticket addNewTicket(@RequestBody @Valid Ticket ticket) {
      return ticket;
    }

    @PutMapping("/ticket")
    public Ticket updateTicket(@RequestBody @Valid Ticket ticket) {
      return ticket;
    }

    @DeleteMapping("/ticket")
    public ErrMsg deleteTicket(@RequestParam Integer id) {
      return new ErrMsg("Ok");
    }
}
