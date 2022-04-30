package com.pk.internal.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.internal.model.ErrMsg;
import com.pk.internal.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtReqFilter extends OncePerRequestFilter {
  @Autowired private CustomUserDetailsService myUserDetailsService;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
      try {
        username = jwtUtil.extractUsername(jwt);
      } catch (ExpiredJwtException e) {
        log.warn("ExpiredJwt caught: {}", e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(objectMapper.writeValueAsString(new ErrMsg("Expired token")));
        return;
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails uDetails = this.myUserDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken uPAuthToken =
          new UsernamePasswordAuthenticationToken(uDetails, null, uDetails.getAuthorities());
      uPAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(uPAuthToken);
    }
    chain.doFilter(request, response);
  }
}
