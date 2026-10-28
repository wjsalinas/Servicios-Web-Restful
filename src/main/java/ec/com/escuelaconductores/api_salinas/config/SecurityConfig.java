package ec.com.escuelaconductores.api_salinas.config;

import ec.com.escuelaconductores.api_salinas.security.JwtAuthenticationFilter;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static ec.com.escuelaconductores.api_salinas.util.Constantes.API;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthChain(HttpSecurity http,
                                              AuthenticationProvider authenticationProvider) {
        http
                .securityMatcher(
                        "/api/escuela/departamentos/**",
                        "/api/escuela/provincia/**",
                        "/api/escuela/districto/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasAnyRole(Constantes.ROL_ADMIN, Constantes.ROL_OPERADOR))
                .httpBasic(basic -> basic.authenticationEntryPoint(this::noAutenticadoBasic))
                .exceptionHandling(ex -> ex.accessDeniedHandler(this::sinPermiso));
        return http.build();
    }

    private void noAutenticadoBasic(HttpServletRequest request, HttpServletResponse response,
                                    AuthenticationException ex) throws IOException {
        String mensaje = (ex instanceof BadCredentialsException)
                ? ex.getMessage() : "Se requiere autenticación (Basic Auth)";
        response.setHeader("WWW-Authenticate", "Basic realm=\"api-salinas\"");
        escribir(response, HttpStatus.UNAUTHORIZED, mensaje, request.getRequestURI());
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                         // .requestMatchers(HttpMethod.POST, "/api/escuela/personas/crear-administrador").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/escuela/personas/crear-persona-login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/escuela/usuarios/login").permitAll()
                        .requestMatchers(API + "/**").hasAnyRole(Constantes.ROL_ADMIN, Constantes.ROL_OPERADOR)
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(this::noAutenticadoJwt)
                        .accessDeniedHandler(this::sinPermiso));
        return http.build();
    }

    private void noAutenticadoJwt(HttpServletRequest request, HttpServletResponse response,
                               AuthenticationException ex) throws IOException {
        Object jwtError = request.getAttribute("jwtError");
        String mensaje = jwtError != null ? jwtError.toString() : "Se requiere un token Bearer válido";
        response.setHeader("WWW-Authenticate", "Bearer");
        escribir(response, HttpStatus.UNAUTHORIZED, mensaje, request.getRequestURI());
    }

    private void sinPermiso(HttpServletRequest request, HttpServletResponse response,
                            AccessDeniedException ex) throws IOException {
        escribir(response, HttpStatus.FORBIDDEN, "No tiene permisos para realizar esta operación", request.getRequestURI());
    }

    private void escribir(HttpServletResponse response, HttpStatus status, String mensaje, String path) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}"
                        .formatted(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
                                mensaje.replace("\"", "'"), path));
    }
}
