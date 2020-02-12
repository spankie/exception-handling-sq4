package com.usbtc.kiosk.models;

import org.springframework.security.core.GrantedAuthority;

/**
 * Roles
 */
public enum Role implements GrantedAuthority {
  ROLE_ADMIN, ROLE_USER;

  @Override
  public String getAuthority() {
    return name();
  }

}