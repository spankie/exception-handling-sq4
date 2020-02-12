package com.usbtc.kiosk.services;

import com.usbtc.kiosk.dto.LoginRequestDTO;
import com.usbtc.kiosk.dto.LoginResponseDTO;
import com.usbtc.kiosk.dto.UserRequestDTO;
import com.usbtc.kiosk.exceptions.CustomException;
import com.usbtc.kiosk.models.User;
import com.usbtc.kiosk.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 */
@Service
public class AuthServiceImpl implements AuthService {
  // @Autowired
  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  @Autowired
  public AuthServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  public User signup(User user) {
    // check if the user with email and username exists
    if (!userRepository.existsByUsername(user.getUsername()) && !userRepository.existsByEmail(user.getEmail())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      // TODO: create a token and find a way to return it.
      return userRepository.save(user);
    } else {
      throw new CustomException("username/email already exists", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public LoginResponseDTO login(LoginRequestDTO user) {
    return new LoginResponseDTO();
  }
}