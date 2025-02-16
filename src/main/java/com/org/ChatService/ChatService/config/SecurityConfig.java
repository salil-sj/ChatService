package com.org.ChatService.ChatService.config;

import com.org.ChatService.ChatService.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import java.security.Provider;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityFilter securityFilter;


    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
   {
        return authConfig.getAuthenticationManager();
   }


   @Bean
   public DaoAuthenticationProvider authenticationProvider()
   {
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder);
       provider.setUserDetailsService(userDetailsService);

       return provider;
   }


    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/user/save" , "/user/login" , "/user/userExists/**" , "/user/userExists/" ,"/ws/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.cors(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
