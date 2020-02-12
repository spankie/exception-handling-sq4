package com.usbtc.kiosk.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * AppConfiguration
 */
@Configuration
public class AppConfiguration {

  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer pc = new PropertySourcesPlaceholderConfigurer();
    pc.setTrimValues(true);
    return pc;
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}