package com.vensys.click_inventory.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {
  private UUID id;
  private String username;
  private String fullname;
  private String email;
  private String role;
}
