package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.RoleRequest;
import com.vensys.click_inventory.DTO.WebResponse;
import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping(path = "/roles",produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<List<Roles>> getAll() {
    List<Roles> roles = roleService.findAll();

    return WebResponse.<List<Roles>>builder()
            .success(true)
            .message("List all Role!")
            .data(roles)
            .build();
  }
}
