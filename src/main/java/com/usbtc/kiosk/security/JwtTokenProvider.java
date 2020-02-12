package com.usbtc.kiosk.security;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.usbtc.kiosk.exceptions.CustomException;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * JwtTokenProvider
 */
@Component
public class JwtTokenProvider {

  @Value("${api.security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${api.security.jwt.token.expire-length:86400000}")
  private long validityInMilliseconds = 86400000;

  // LETS SEE IF THIS WOULD OVERRIDE THE DEFAULT
  @Autowired
  private UserDetailsService myUserDetails;

  @PostConstruct
  public void init() {
    // test other encoders
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // create token from username and roles
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // TODO: check the meaning of JWS
      return true;
    } catch (JwtException | IllegalArgumentException e) { // maybe other exceptions should be handled
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
      // maybe this should return a false... tinker with this
    }
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // getUsername retrieves the username of the loggedin user from the subject of
  // the jwt.
  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }
}