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

        System.out.println("PASSWORD : " +this.passwordEncoder.encode("1606"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Désactive CSRF avec la nouvelle syntaxe
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
                        // plusieur roles
                        .requestMatchers("/compte/allCompte").hasAnyRole("client","admin","client_bloque","animateur")
                        .requestMatchers("/appel/gererAbsence").hasRole("animateur")
                        .requestMatchers("/appel/debloquerCompte").hasRole("admin")
                        .requestMatchers("/compte/compteBloque").hasRole("admin")
                        .requestMatchers("/crenaux/allCrenaux").hasAnyRole("client","admin","animateur")
                        .requestMatchers("/inscription/insertOrUpdateInscription").hasAnyRole("client","admin","animateur")
                        .requestMatchers("/inscription/deleteInscription").hasAnyRole("client","admin","animateur")
                        .requestMatchers("/inscription/getRegisteredUsers/{activiteId}").hasAnyRole("admin","animateur")
                        // un seul role
                        .requestMatchers("/compte/**").hasRole("client")
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