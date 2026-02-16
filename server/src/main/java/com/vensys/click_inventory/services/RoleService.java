package com.vensys.click_inventory.services;

import com.vensys.click_inventory.DTO.RoleRequest;
import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private Validator validator;

  @Transactional
  public void create(RoleRequest request){
    Set<ConstraintViolation<RoleRequest>> constraintViolations = validator.validate(request);

    if(!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    if(roleRepository.existsByName(request.getName())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name udah ada");
    }

    Roles role = new Roles();
    role.setName(request.getName());

    roleRepository.save(role);
  }

  public List<Roles> findAll(){
    return roleRepository.findAll();
  }
}
