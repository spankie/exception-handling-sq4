package com.usbtc.kiosk.controllers;

import javax.validation.Valid;

import com.usbtc.kiosk.apiresponse.ApiResponse;
import com.usbtc.kiosk.dto.LoginRequestDTO;
import com.usbtc.kiosk.dto.UserRequestDTO;
import com.usbtc.kiosk.models.User;
import com.usbtc.kiosk.services.AuthService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 */
@RestController
@RequestMapping("auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserRequestDTO user) {
    User newuser = authService.signup(modelMapper.map(user, User.class));
    ApiResponse ar = new ApiResponse(HttpStatus.CREATED);
    ar.setMessage("Successfully signed up");
    return new ResponseEntity<>(ar, HttpStatus.CREATED);
  }

  // @PostMapping("/login")
  // public ResponseEntity<String> login(@RequestBody LoginRequestDTO user) {
  // User newuser = authService.login(modelMapper.map(user, User.class));
  // return new ResponseEntity<>(newuser.getUsername(), HttpStatus.CREATED);
  // }
}