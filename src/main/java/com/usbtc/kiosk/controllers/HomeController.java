package com.usbtc.kiosk.controllers;

import com.usbtc.kiosk.configurations.ApiConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 */
@RestController
@RequestMapping("/home")
public class HomeController {

  @Autowired
  private ApiConfiguration apiConfiguration;

  @Value("${api.spankie.from:Hi}") // sets the default to "Hi"
  private String from;

  @GetMapping
  public String name() {
    return apiConfiguration.getFrom() + " " + from;
  }
}