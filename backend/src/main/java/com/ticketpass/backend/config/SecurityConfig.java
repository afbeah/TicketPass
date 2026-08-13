package com.ticketpass.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> {})

                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/login")
                        .permitAll()

                        // Busca de eventos externos - pública
                        .requestMatchers(HttpMethod.GET, "/api/events")
                        .permitAll()

                        // Eventos TicketPass disponíveis para compra - pública
                        .requestMatchers(HttpMethod.GET, "/api/events/local")
                        .permitAll()

                        // Criação de eventos - somente ORGANIZER
                        .requestMatchers(HttpMethod.POST, "/api/events")
                        .hasRole("ORGANIZER")

                        // Demais operações de eventos - somente ORGANIZER
                        .requestMatchers("/api/events/**")
                        .hasRole("ORGANIZER")

                        // Reservas - somente CUSTOMER
                        .requestMatchers("/api/reservations/**")
                        .hasRole("CUSTOMER")

                        // Pagamentos - somente CUSTOMER
                        .requestMatchers("/api/payments/**")
                        .hasRole("CUSTOMER")

                        // Validação de ingresso - somente GATEKEEPER
                        .requestMatchers("/api/validation")
                        .hasRole("GATEKEEPER")

                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = jwt.getClaimAsString("role");

            if (role == null || role.isBlank()) {
                return List.of();
            }

            return List.of(
                    new SimpleGrantedAuthority("ROLE_" + role)
            );
        });

        return converter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        byte[] secretKeyBytes = Base64.getDecoder().decode(jwtSecret);

        SecretKeySpec secretKey = new SecretKeySpec(
                secretKeyBytes,
                "HmacSHA256"
        );

        return new NimbusJwtEncoder(
                new com.nimbusds.jose.jwk.source.ImmutableSecret<>(secretKey)
        );
    }

    @Bean
    JwtDecoder jwtDecoder() {
        byte[] secretKeyBytes = Base64.getDecoder().decode(jwtSecret);

        SecretKeySpec secretKey = new SecretKeySpec(
                secretKeyBytes,
                "HmacSHA256"
        );

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}