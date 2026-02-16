package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.RoleRequest;
import com.vensys.click_inventory.DTO.WebResponse;
import com.vensys.click_inventory.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController {
  @Autowired
  private RoleService roleService;
  
  @PostMapping(
          path = "/roles",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<String> create(@Valid @RequestBody RoleRequest request) {
    roleService.create(request);

    return WebResponse.<String>builder()
            .success(true)
            .message("Create Role Success")
            .build();
  }
}
