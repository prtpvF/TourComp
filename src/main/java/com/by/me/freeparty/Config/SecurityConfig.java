package com.by.me.freeparty.Config;

import com.by.me.freeparty.Services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                http.authorizeHttpRequests(requests->requests
                        .requestMatchers("/auth/login","/auth/registration","/error","/css/*", "/org/login", "/about", "/", "/tour/all", "/offer", "/all",  "/image/**", "/js/**, /tour/info/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form->form.loginPage("/auth/login").loginProcessingUrl("/process_login").failureForwardUrl("/auth/login?error").defaultSuccessUrl("/person/home", true).permitAll()

                ).logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/auth/login"));


        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, @Autowired PersonServices personServices)
            throws Exception

    {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(personServices)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}