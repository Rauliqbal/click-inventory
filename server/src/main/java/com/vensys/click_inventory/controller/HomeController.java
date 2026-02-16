package com.vensys.click_inventory.controller;

import com.vensys.click_inventory.DTO.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class HomeController {

  @GetMapping("/hello")
  public WebResponse<String> helloWorld(){
    return WebResponse.<String>builder()
            .success(true)
            .message("Hello World!")
            .data("Hello World! Click Inventory")
            .build();
  }
}
