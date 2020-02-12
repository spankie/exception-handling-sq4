package com.usbtc.kiosk.services;

import com.usbtc.kiosk.dto.LoginRequestDTO;
import com.usbtc.kiosk.dto.LoginResponseDTO;
import com.usbtc.kiosk.models.User;

/**
 * AuthService
 */
public interface AuthService {
  public User signup(User user);

  public LoginResponseDTO login(LoginRequestDTO user);
}