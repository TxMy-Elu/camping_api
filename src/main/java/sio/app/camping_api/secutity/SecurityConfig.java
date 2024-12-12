package sio.app.camping_api.secutity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sio.app.camping_api.services.CustomCompteDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final CustomCompteDetailsService customCompteDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthTokenFilter jwtAuthTokenFilter, CustomCompteDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtAuthTokenFilter = jwtAuthTokenFilter;
        this.customCompteDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;

        System.out.println(this.passwordEncoder.encode("password123"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Désactive CSRF avec la nouvelle syntaxe
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        // Permettre l'accès libre aux routes d 'authentification
                        .requestMatchers("/metrics").hasRole("USER")
                        // Autoriser uniquement les USER
                        .requestMatchers("/metrics/all").hasRole("ADMIN") // Autoriser uniquement les USER
                        .anyRequest().authenticated() // Toutes les autres routes doivent être authentifiées
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Gestion de session sans état
                );
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}