package com.vensys.click_inventory.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
  private String username;
  private String fullname;
  private String email;
  private String role;
}
