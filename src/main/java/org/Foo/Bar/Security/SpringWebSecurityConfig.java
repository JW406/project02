package org.Foo.Bar.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtTokenFilter filter;
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests() //
        .antMatchers("/api/**") //
        .authenticated() //
        .antMatchers("/pay/create-checkout-session") //
        .permitAll() //
        .antMatchers("/pay/**") //
        .authenticated() //
        .anyRequest() //
        .permitAll() //
        .and() //
        .exceptionHandling() //
        .accessDeniedHandler(accessDeniedHandler) //
        .authenticationEntryPoint(authenticationEntryPoint) //
        .and() //
        .sessionManagement() //
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
        .and().csrf().disable() //
    ;
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
