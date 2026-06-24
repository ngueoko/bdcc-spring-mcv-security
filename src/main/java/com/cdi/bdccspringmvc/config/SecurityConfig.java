package com.cdi.bdccspringmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){

        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN").build()

        );
    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return  http
            .formLogin(fl->fl.loginPage("/login").permitAll())
            //.formLogin(Customizer.withDefaults())
 //           .csrf(csrf->csrf.disable())  //désactivation
           // .authorizeHttpRequests(arg-> arg.requestMatchers("/user/**").hasRole("USER"))
           // .authorizeHttpRequests(arg-> arg.requestMatchers("/admin/**").hasRole("ADMIN"))
            .authorizeHttpRequests(arg-> arg.requestMatchers("/public/**","/webjars/**","/login").permitAll())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .exceptionHandling(eh->eh.accessDeniedPage("/notAuthorized"))
            .build();

}

}