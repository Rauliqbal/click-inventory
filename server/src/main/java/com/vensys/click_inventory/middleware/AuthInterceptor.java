package com.vensys.click_inventory.middleware;

import com.vensys.click_inventory.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Cookie[] cookies = request.getCookies();
    String token = null;

    if(cookies != null) {
      for (Cookie cookie : cookies) {
        if("ACCESS_TOKEN".equals(cookie.getName())) {
          token = cookie.getValue();
        }
      }
    }

    if(token == null || token.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized");
    }

    try {
      String username = jwtUtil.extractUsername(token);

      request.setAttribute("username", username);
      return true;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session expired, please login");
    }

  }
}
