package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.LoginUserRequest;
import com.vensys.click_inventory.DTO.RegisterUserRequest;
import com.vensys.click_inventory.DTO.UserResponse;
import com.vensys.click_inventory.DTO.WebResponse;
import com.vensys.click_inventory.entity.Users;
import com.vensys.click_inventory.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  private UserService userService;

  //  REGISTER
  @PostMapping(
          path = "/auth/register",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<UserResponse> register(@RequestBody RegisterUserRequest request) {
    Users user = userService.register(request);

    UserResponse response = UserResponse.builder()
            .username(user.getUsername())
            .fullname(user.getFullname())
            .email(user.getEmail())
            .role(user.getRole().getName())
            .build();

    return WebResponse.<UserResponse>builder()
            .success(true)
            .message("Register Success")
            .data(response)
            .build();
  }

  //  LOGIN
  @PostMapping(
          path = "/auth/login",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<Map<String, String>> login(@RequestBody LoginUserRequest request, HttpServletResponse response) {
    String token = userService.login(request);

    Cookie cookie = new Cookie("ACCESS_TOKEN", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(86400); // 1 day
    response.addCookie(cookie);

    Map<String, String> data = new HashMap<>();
    data.put("token", token);

    return WebResponse.<Map<String, String>>builder()
            .success(true)
            .message("Login Success")
            .data(data)
            .build();
  }

  //  GET USER
  @GetMapping(
          path = "/user",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<UserResponse> getUser(HttpServletRequest request) {
    String username = (String) request.getAttribute("username");

    System.out.println("Username dari Interceptor: " + username);

    UserResponse userResponse = userService.getByUsername(username);

    return WebResponse.<UserResponse>builder()
            .success(true)
            .message("Get user success")
            .data(userResponse)
            .build();
  }
}
