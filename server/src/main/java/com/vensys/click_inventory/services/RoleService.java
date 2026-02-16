package com.vensys.click_inventory.services;

import com.vensys.click_inventory.DTO.RoleRequest;
import com.vensys.click_inventory.DTO.RoleResponse;
import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.repositories.RoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private Validator validator;

  @Transactional
  public void create(RoleRequest request) {
    Set<ConstraintViolation<RoleRequest>> constraintViolations = validator.validate(request);

    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    if (roleRepository.existsByName(request.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name udah ada");
    }

    Roles role = new Roles();
    role.setName(request.getName());

    roleRepository.save(role);
  }

  @Transactional(readOnly = true)
  public List<Roles> findAll() {
    return roleRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Roles findById(UUID id) {
    return roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));
  }

  @Transactional
  public RoleResponse update(RoleResponse request, UUID id) {
    Set<ConstraintViolation<RoleRequest>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    Roles role = roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role tidak ditemukan"));

    if (roleRepository.existsByNameAndIdNot(request.getName(), id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama role sudah digunakan");
    }

    role.setName(request.getName());
    roleRepository.save(role);

    return RoleResponse.builder()
            .name(role.getName())
            .build();
  }
}

