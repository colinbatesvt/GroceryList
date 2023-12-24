package com.github.colinbatesvt.grocerylist.security;

import com.github.colinbatesvt.grocerylist.model.auth.Role;
import com.github.colinbatesvt.grocerylist.security.filter.AuthenticationFilter;
import com.github.colinbatesvt.grocerylist.security.filter.ExceptionHandlerFilter;
import com.github.colinbatesvt.grocerylist.security.filter.JWTAuthorizationFilter;
import com.github.colinbatesvt.grocerylist.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Value("${jwt.secretkey}")
    private String secretKey;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://localhost:3000");
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(-1L);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, secretKey);
        authenticationFilter.setFilterProcessesUrl("/user/authenticate");
        http
                .cors(withDefaults())
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(secretKey), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
