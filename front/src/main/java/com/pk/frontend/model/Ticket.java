package com.pk.frontend.model;

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
  String name;
  String surname;
  String city;
  String street;
  Integer buildingNumber;
}
