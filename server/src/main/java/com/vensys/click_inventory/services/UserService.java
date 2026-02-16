package com.vensys.click_inventory.services;

import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.entity.Users;
import com.vensys.click_inventory.DTO.RegisterUserRequest;
import com.vensys.click_inventory.repositories.RoleRepository;
import com.vensys.click_inventory.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private Validator validator;

  @Transactional
  public Users register(RegisterUserRequest request) {
    Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);

    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username udah ada");
    }

    Roles role = roleRepository.findByName(request.getRole())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    System.out.println("Role ditemukan: " + role.getName() + " dengan ID: " + role.getId());

    Users user = new Users();
    user.setUsername(request.getUsername());
    user.setFullname(request.getFullname());
    user.setEmail(request.getEmail());
    user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
    user.setRole(role);

    userRepository.save(user);
    return user;
  }
}
