package com.easy_flow_server.security;

import com.easy_flow_server.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.bind.annotation.CrossOrigin;


@Configuration
@CrossOrigin
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Autowired
    private UserPrincipleDetailsService userPrincipleDetailsService;

    @Autowired
    private UserRepositry userRepositry;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserPrincipleDetailsService userPrincipleDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userPrincipleDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //JWT
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, final AuthenticationManagerBuilder auth, final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        auth.authenticationProvider(authenticationProvider());

        http
                //remove csrf and state because in jwt we do not need them
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // add jwt filters (first is authentication, second is Authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), this.userRepositry))
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/Register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/passenger/**").hasRole("PASSENGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/plan/**").hasAnyRole("ADMIN", "PASSENGER")
                        .requestMatchers(HttpMethod.GET, "/plans/**").hasAnyRole("ADMIN", "PASSENGER")
                        .requestMatchers("/plan/**").hasRole("ADMIN")
                        .requestMatchers("/plans/**").hasRole("ADMIN")

                        .requestMatchers("/**").permitAll()

//                        .requestMatchers("/api/public/test").permitAll()
//                        .requestMatchers("/api/public/management/*").hasRole("MANAGER")
//                        .requestMatchers("/api/public/admin/*").hasRole("ADMIN")
                        .anyRequest().authenticated()).
                headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"));

        //config access rules
        http.cors();
        return http.build();
    }

    //DataBase Authentication
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //we say that database Authentication will use below password Encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipleDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
