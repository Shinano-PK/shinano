package com.pk.frontend.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
  public Integer ticketId;
  public Integer userId;
  public Integer flightId;
  public Date reservedDate;
  public Date boughtDate;
  public Integer price;
  public Integer status;
  public String name;
  public String surname;
  public String city;
  public String street;
  public Integer buildingNumber;
}
