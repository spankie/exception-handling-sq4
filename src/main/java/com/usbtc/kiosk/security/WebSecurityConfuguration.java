package com.usbtc.kiosk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * WebSecurityConfuguration Overrides the spring security configuration by
 * extending the abstract class WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfuguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void configure(WebSecurity web) throws Exception {
    // configure security for http requests.
    super.configure(web);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // We configure security for web pages...

    // Disable CSRF (cross site request forgery)
    // enabled by default
    http.csrf().disable();

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // set authentication policies for each routes
    http.authorizeRequests()
        // list out the routes not to authenticate. (without context prefix)
        .antMatchers("/auth/signup").permitAll().antMatchers("/home").permitAll()
        // set the rest to be authenticated.
        .anyRequest().authenticated();

    // define what to do when user attempt to access and authenticated route without
    // proper authorizations
    http.exceptionHandling().accessDeniedPage("/api/v1/auth/login");
    // accessDeniedHandler() // specify the handler to handle access denied

    // create a new SecurityConfigurerAdapter<DefaultSecurityFilterChain,
    // HttpSecurity>
    // SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
    // jwtConfigurer = new JwtTokenFilterConfigurer(
    // jwtProvider);

    // Apply JWT Filter to handle authentication/Authorization
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    // Optional, if you want to test the API from a browser
    // http.httpBasic();
  }

  // Create a bean to handle password encoding.
  // @Bean
  // public PasswordEncoder passwordEncoder() {
  // return new BCryptPasswordEncoder(12);
  // }

  // @Bean
  // public PasswordEncoder passwordEncoder() {
  // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  // }

}