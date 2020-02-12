package com.usbtc.kiosk.security;

import com.usbtc.kiosk.models.User;
import com.usbtc.kiosk.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * MyUserDetails
 */
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with '" + username + "' not found");
    }
    return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword())
        .authorities(user.getRoles()).accountExpired(false).accountLocked(false).credentialsExpired(false)
        .disabled(false).build();
  }

}