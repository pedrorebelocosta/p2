package org.nolhtaced.webapi.config;

import lombok.RequiredArgsConstructor;
import org.nolhtaced.webapi.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/api/products",
            "/api/services",
            "/demo/hello",
            "demo/hello",
            "api/demo/hello",
            "/api/demo/hello",
            "/api/auth/**"
    };
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    // private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().permitAll()
//                    req.requestMatchers(WHITE_LIST_URLS).permitAll()
//                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout(logout -> {
//                    logout.logoutUrl("/api/user/me/logout")
//                            .addLogoutHandler(logoutHandler)
//                            .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext());
//                });
        return http.build();
    }
}
