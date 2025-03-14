package sio.app.camping_api.security;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import sio.app.camping_api.secutity.JwtAuthTokenFilter;
import sio.app.camping_api.services.CustomCompteDetailsService;

import java.util.List;

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

        System.out.println("PASSWORD : " + this.passwordEncoder.encode("1606"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Activation correcte de CORS
            .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/compte/allCompte").hasAnyRole("client", "admin", "client_bloque", "animateur")
                .requestMatchers("/appel/gererAbsence").hasAnyRole("animateur", "admin")
                .requestMatchers("/appel/debloquerCompte").hasRole("admin")
                .requestMatchers("/compte/compteBloque").hasRole("admin")
                .requestMatchers("/creneaux/allCreneaux").hasAnyRole("client", "admin", "animateur")
                .requestMatchers("/inscription/insertOrUpdateInscription").hasAnyRole("client", "admin", "animateur")
                .requestMatchers("/inscription/deleteInscription").hasAnyRole("client", "admin", "animateur")
                .requestMatchers("/inscription/getRegisteredUsers/{activiteId}").hasAnyRole("admin", "animateur", "client")
                .requestMatchers("/compte/**").hasRole("client")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Autorise React
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
