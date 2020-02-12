package com.usbtc.kiosk.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usbtc.kiosk.controllers.AuthController;
import com.usbtc.kiosk.dto.UserRequestDTO;
import com.usbtc.kiosk.exceptions.CustomException;
import com.usbtc.kiosk.models.Role;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;

/**
 * AuthControllerTests
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // Allows the mockmvc to be autowired for us
public class AuthControllerTest {
  @Autowired
  private AuthController authController;
  @Autowired
  private MockMvc mockMvc;

  public AuthControllerTest() {

  }

  @Test
  public void contextLoads() {
    assertNotNull(authController);
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void SignupWithCorrectValues() {
    // run signup here.
    UserRequestDTO userRequest = new UserRequestDTO("Spankie Dee", "spankie@gmail.com", "spankie_dee", "password",
        "address", Arrays.asList(Role.ROLE_ADMIN));
    try {
      this.mockMvc
          .perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userRequest)))
          .andDo(print()).andExpect(status().isCreated())
          .andExpect(content().string(containsString(userRequest.getUsername())));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void SignupWithExisitingValues() {
    // run signup here.
    UserRequestDTO userRequest = new UserRequestDTO("Spankie Exist", "spankie_exist@gmail.com", "spankie_exist",
        "password", "address", Arrays.asList(Role.ROLE_ADMIN));
    String userRequestJson = asJsonString(userRequest);
    try {
      this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
          .andDo(print()).andExpect(status().isCreated())
          .andExpect(content().string(containsString(userRequest.getUsername())));
    } catch (Exception e) {
      fail(e.getMessage());
    }

    try {
      this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
          .andDo(print());
      fail();
    } catch (Exception e) {
      if (!e.getMessage().contains("username/email already exists")) {
        fail("custom exception should be thrown: " + e.getMessage());
      }
    }
  }

  @Test
  public void LoginWithCorrectDetails() {
    UserRequestDTO userRequest = new UserRequestDTO("Dee Spankie", "dee@gmail.com", "dee_spankie", "password",
        "address", Arrays.asList(Role.ROLE_ADMIN));
    String userRequestJson = asJsonString(userRequest);
    try {
      this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
          .andDo(print()).andExpect(status().isCreated())
          .andExpect(content().string(containsString(userRequest.getUsername())));
    } catch (Exception e) {
      fail(e.getMessage());
    }

    try {
      this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
          .andDo(print()).andExpect(status().isOk())
          .andExpect(content().string(containsString(userRequest.getUsername())));
    } catch (Exception e) {
      fail("should not throw an exception with correct details");
    }
  }

  @Test
  public void LoginWithInCorrectDetails() {
    String userRequestJson = "{username: 'doesnotexist@gmail.com', password: 'password'}";
    try {
      this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
          .andDo(print());
      fail();
    } catch (Exception e) {
      // check for the exception here....
      fail("should not throw an exception with correct details");
    }
  }
}