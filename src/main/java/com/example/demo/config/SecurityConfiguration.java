package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf()
      .disable()
      .cors()
      .and()
      .authorizeHttpRequests()
      .requestMatchers("/api/authentication/**")
      .permitAll()
      .requestMatchers("/api/user/**")
      .permitAll()
      .requestMatchers("/api/admin/**")
      .hasRole("ADMIN")
      .anyRequest()
      .authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout()
      .logoutUrl("/api/v1/auth/logout")
      .addLogoutHandler(logoutHandler)
      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
    return httpSecurity.build();
  }


//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors();//cross-origin-resource-sharing
//    http.csrf().disable();
//    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//  http.authorizeRequests()
//    .antMatchers("/api/authentication/**").permitAll()//login and register pre-path
//    .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
//    .antMatchers("/api/groups/**").permitAll()
//    .antMatchers("/api/user/**").permitAll()
//    .anyRequest().authenticated();
//
//  http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//  }

}
