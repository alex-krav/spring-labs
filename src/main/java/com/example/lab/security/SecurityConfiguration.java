package com.example.lab.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String adminPassword = bCryptEncoder.encode("admin");
        auth.inMemoryAuthentication()
                .withUser("admin").password(adminPassword).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/books").permitAll()
                .antMatchers("/books/**/edit").hasRole("ADMIN")
                .antMatchers("/books/**/delete").hasRole("ADMIN")
                .antMatchers("/books/add").hasRole("ADMIN")
                .antMatchers("/api/v1/**").hasRole("ADMIN")
                .and()
                .formLogin();

        http       //other configure params.
                .csrf().disable();

    }

}
