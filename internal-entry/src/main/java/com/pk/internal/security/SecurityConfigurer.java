package com.pk.internal.security;

import javax.sql.DataSource;

import com.pk.internal.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
  @Autowired private CustomUserDetailsService userDetailsService;
  @Autowired private JwtReqFilter jwtReqFilter;
  // @Autowired DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
    // httpSecurity
    //     .csrf()
    //     .disable()
    //     .authorizeRequests()
    //     .antMatchers("/auth/login")
    //     .permitAll()
    //     .antMatchers("/auth/register")
    //     .permitAll()
    //     .antMatchers("/auth/verifyAcc/**")
    //     .permitAll()
    //     .antMatchers("/auth/verifyPassword/**")
    //     .permitAll()
    //     .antMatchers("/auth/resetPassword/**")
    //     .permitAll()
    //     .antMatchers("/h2-console/**")
    //     .permitAll()
    //     .anyRequest()
    //     .authenticated()
    //     .and()
    //     .sessionManagement()
    //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // httpSecurity.addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    // return NoOpPasswordEncoder.getInstance();
  }
}