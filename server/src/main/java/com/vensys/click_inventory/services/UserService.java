package com.vensys.click_inventory.services;

import com.vensys.click_inventory.DTO.LoginUserRequest;
import com.vensys.click_inventory.DTO.UserResponse;
import com.vensys.click_inventory.entity.Roles;
import com.vensys.click_inventory.entity.Users;
import com.vensys.click_inventory.DTO.UserRequest;
import com.vensys.click_inventory.repositories.RoleRepository;
import com.vensys.click_inventory.repositories.UserRepository;
import com.vensys.click_inventory.utils.JwtUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private Validator validator;

  @Transactional
  public Users register(UserRequest request) {
    Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username udah ada");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email udah ada");
    }

    Roles role = roleRepository.findByName(request.getRole())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + request.getRole() + " not found"));

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

  @Transactional
  public String login(LoginUserRequest request) {
    Set<ConstraintViolation<LoginUserRequest>> constraintViolations = validator.validate(request);

    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    Users user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau Password salah"));

    if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau Password salah");
    }

    return jwtUtil.generateToken(request.getUsername());
  }

  @Transactional(readOnly = true)
  public UserResponse getByUsername(String username) {
    Users user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .fullname(user.getFullname())
            .email(user.getEmail())
            .role(user.getRole().getName())
            .build();
  }

  @Transactional
  public UserResponse update(UUID id, UserRequest request) {
    Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    Users user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    // Validasi Input username & email
    if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usernamae is already");
    }

    if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already");
    }

    Roles role = roleRepository.findByName(request.getRole())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
      user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
    }

    // Update Object
    user.setUsername(request.getUsername());
    user.setFullname(request.getFullname());
    user.setEmail(request.getEmail());
    user.setRole(role);

    userRepository.save(user);

    return UserResponse.builder()
            .username(user.getUsername())
            .fullname(user.getFullname())
            .email(user.getEmail())
            .role(user.getRole().getName())
            .build();

  }

  @Transactional
  public UserResponse delete(UUID id) {
    Users user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    userRepository.delete(user);

    return UserResponse.builder()
            .fullname(user.getFullname())
            .build();
  }

//  @Transactional(readOnly = true)
//  public UserResponse get(String token) {
//    String username = jwtUtil.extractUsername(token);
//
//    Users user = userRepository.findByUsername(username)
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//    return UserResponse.builder()
//            .username(user.getUsername())
//            .fullname(user.getFullname())
//            .email(user.getEmail())
//            .role(user.getRole().getName())
//            .build();
//  }
}
