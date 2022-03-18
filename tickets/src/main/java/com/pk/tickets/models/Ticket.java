package com.pk.tickets.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ticket {
  Integer ticketId;
  Integer userId;
  Integer flightId;
  Date reservedDate;
  Date boughtDate;
  Integer price;
  Integer status;
}
