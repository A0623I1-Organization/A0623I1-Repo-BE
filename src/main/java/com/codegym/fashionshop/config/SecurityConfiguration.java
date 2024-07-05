//package com.codegym.fashionshop.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
//        this.jwtAuthFilter = jwtAuthFilter;
//        this.authenticationProvider = authenticationProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Sử dụng phương pháp mới để vô hiệu hóa CSRF
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/auth/**").permitAll()  // Điều chỉnh mẫu endpoint khi cần
//                        .requestMatchers("/admin-user/**").permitAll()  // Điều chỉnh mẫu endpoint khi cần
//                        .requestMatchers(HttpMethod.GET).permitAll()  // Điều chỉnh mẫu endpoint khi cần
//                        .requestMatchers("/users").authenticated()
//                        .requestMatchers(HttpMethod.POST).authenticated()
//                        .requestMatchers(HttpMethod.PUT).authenticated()
//                        .requestMatchers(HttpMethod.DELETE).authenticated()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
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
                        .requestMatchers("/auth/**","api/public/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll() /// Tạm thời không xác thực, sau đổi sang .authenticated()
<<<<<<< HEAD
=======
                        .requestMatchers("/api/auth/notification/create").permitAll() // Tạm thời không xác thực, sau đổi sang .authenticated()
                        .requestMatchers("/api/users/**", "/api/dashboard/**", "/auth/get-profile", "/auth/update-password/{userId}").authenticated()
>>>>>>> d4e8968b4b0cb61f0104036594b99df1442823d3
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
