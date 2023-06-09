package com.example.demo.controller;

import com.example.demo.service.AuthenticationService;
import com.example.demo.service.LogoutService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  private final LogoutService logoutService;

  @Autowired
  private UserService userService;

  @PostMapping("/sign-up")
  public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request
  ) {
    if (userService.isEmailExists(request.getEmail())) {
      AuthenticationResponse response = new AuthenticationResponse();
      response.setMessage("Email already exists");
      return ResponseEntity.badRequest().body(response);
    }
    if (userService.isUsernameExists(request.getUsername())) {
      AuthenticationResponse response = new AuthenticationResponse();
      response.setMessage("Username already exists");
      return ResponseEntity.badRequest().body(response);
    }
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<AuthenticationResponse> authenticate(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    authenticationService.refreshToken(request, response);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) {
    logoutService.logout(request, response, authentication);
    return ResponseEntity.ok("Logout successful");
  }

}
