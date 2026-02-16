package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.RoleRequest;
import com.vensys.click_inventory.DTO.RoleResponse;
import com.vensys.click_inventory.DTO.WebResponse;
import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @PostMapping(
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

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<List<Roles>> getAll() {
    List<Roles> roles = roleService.findAll();

    return WebResponse.<List<Roles>>builder()
            .success(true)
            .message("List all Role!")
            .data(roles)
            .build();
  }

  @GetMapping(path = "/{id}")
  public WebResponse<RoleResponse> getById(@PathVariable("id") UUID id) {
    Roles role = roleService.findById(id);

    RoleResponse response = RoleResponse.builder()
            .name(role.getName())
            .build();

    return WebResponse.<RoleResponse>builder()
            .success(true)
            .message("Get Role By ID: " + id)
            .data(response)
            .build();
  }

  @PutMapping(
          path = "/{id}",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<RoleResponse> update(@PathVariable("id") UUID id, @RequestBody RoleRequest request) {
    RoleResponse response = roleService.update(id, request);

    return WebResponse.<RoleResponse>builder()
            .success(true)
            .message("Update " + response.getName() + " Successfully!")
            .data(response)
            .build();
  }

  @DeleteMapping(
          path = "/{id}",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<RoleResponse> delete(@PathVariable("id") UUID id){
    RoleResponse response = roleService.delete(id);

    return WebResponse.<RoleResponse>builder()
            .success(true)
            .message("Delete " + response.getName() + " Successfully!")
            .build();
  }
}
