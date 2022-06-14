package com.pk.internal.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
