package com.usbtc.kiosk.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.usbtc.kiosk.models.Role;

/**
 * UserRequestDTO
 */
public class UserRequestDTO {
  @NotNull
  @NotBlank
  @JsonAlias({ "full_name" })
  private String fullName;
  @Email
  @NotNull
  @NotBlank
  private String email;
  @NotNull
  @NotBlank
  @Size(min = 5)
  private String username;
  @NotNull
  @NotBlank
  @Size(min = 6)
  private String password;
  @NotBlank
  @NotNull
  private String address;
  private List<Role> roles;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserRequestDTO(String fullName, String email, String username, String password, String address,
      List<Role> roles) {
    this.fullName = fullName;
    this.email = email;
    this.username = username;
    this.password = password;
    this.address = address;
    this.roles = roles;
  }

}