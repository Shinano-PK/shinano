package com.pk.email.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDB {
  Integer emailId;
  String srcAddress;  
  String dstAddress;  
  String subject;  
  String message;  
  String messageType;  
  Date sentAt;  
}
