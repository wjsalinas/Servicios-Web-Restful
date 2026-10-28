package ec.com.escuelaconductores.api_salinas.security;


import ec.com.escuelaconductores.api_salinas.entity.UsuarioEntity;
import ec.com.escuelaconductores.api_salinas.repository.UsuarioRepository;
import ec.com.escuelaconductores.api_salinas.util.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class AuthenticationUser implements AuthenticationProvider {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(noRollbackFor = AuthenticationException.class)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = Objects.toString(authentication.getCredentials(), "");
        UsuarioEntity usuario = usuarioRepository.findByUsuarioAndEstado(username, Constantes.ACTIVO)
                .orElseThrow(() -> new BadCredentialsException("Usuario no válido."));
        if (!Encrypt.verifyPassword(password, usuario.getClave(), usuario.getSalt())) {
            usuarioRepository.save(usuario);
            throw new BadCredentialsException("Usuario o clave incorrectos.");
        }
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return UsernamePasswordAuthenticationToken.authenticated(usuario.getUsuario(), null,
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
