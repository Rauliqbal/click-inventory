package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.RegisterUserRequest;
import com.vensys.click_inventory.DTO.UserResponse;
import com.vensys.click_inventory.DTO.WebResponse;
import com.vensys.click_inventory.entity.Users;
import com.vensys.click_inventory.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/user",
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
}
