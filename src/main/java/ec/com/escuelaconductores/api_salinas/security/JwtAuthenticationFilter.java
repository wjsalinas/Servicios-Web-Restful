package ec.com.escuelaconductores.api_salinas.security;

import ec.com.escuelaconductores.api_salinas.exceptions.JwtAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIJO = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(PREFIJO)) {
            try {
                JwtUtil.TokenClaims claims = jwtUtil.validar(header.substring(PREFIJO.length()));
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + claims.rol()));
                var auth = UsernamePasswordAuthenticationToken.authenticated(claims.username(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtAuthException ex) {
                SecurityContextHolder.clearContext();
                request.setAttribute("jwtError", ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
