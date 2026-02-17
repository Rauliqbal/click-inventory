package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.*;
import com.vensys.click_inventory.entity.Users;
import com.vensys.click_inventory.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
  public WebResponse<UserResponse> register(@RequestBody UserRequest request) {
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

  // LOGOUT
  @PostMapping(
          path = "/auth/logout",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<String> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("ACCESS_TOKEN", null);

    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);

    response.addCookie(cookie);

    return WebResponse.<String>builder()
            .success(true)
            .message("Logout Success")
            .data("OK")
            .build();
  }

  // GET USER
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

  // UPDATE USER
  @PutMapping(
          path = "/user/{id}",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<UserResponse> update(@PathVariable("id") UUID id, @RequestBody UserRequest request) {
    UserResponse response = userService.update(id, request);

    return WebResponse.<UserResponse>builder()
            .success(true)
            .message("Update " + response.getFullname() + " Successfully!")
            .data(response)
            .build();
  }

//  DELETE USER
  @DeleteMapping(
          path = "/user/{id}",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public WebResponse<RoleResponse> delete(@PathVariable("id") UUID id) {
    UserResponse response = userService.delete(id);

    return WebResponse.<RoleResponse>builder()
            .success(true)
            .message("Delete " + response.getFullname() + " Successfully!")
            .build();
  }
}
