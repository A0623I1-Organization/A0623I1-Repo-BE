package com.codegym.fashionshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Sử dụng phương pháp mới để vô hiệu hóa CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()  // Điều chỉnh mẫu endpoint khi cần
                        .requestMatchers("/admin-user/**").permitAll()  // Điều chỉnh mẫu endpoint khi cần
                        .requestMatchers("/pricing/**").permitAll()  // Thêm quy tắc cho endpoint /pricing
                        .requestMatchers("/products/**").permitAll()  // Thêm quy tắc cho endpoint /pricing
                        .requestMatchers("/api/bills/**").permitAll()  // Thêm quy tắc cho endpoint /pricing
                        .requestMatchers("/api/bill-items/**").permitAll()  // Thêm quy tắc cho endpoint /pricing
                        .requestMatchers(HttpMethod.POST, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}