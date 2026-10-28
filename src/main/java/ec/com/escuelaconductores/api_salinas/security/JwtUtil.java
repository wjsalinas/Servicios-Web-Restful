package ec.com.escuelaconductores.api_salinas.security;

import ec.com.escuelaconductores.api_salinas.exceptions.JwtAuthException;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class JwtUtil {
    private static final String ALGORITMO_MAC = "HmacSHA256";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    private final SecretKeySpec secretKey;
    private final long expiracionSegundos;

    public JwtUtil(@Value("${app.security.jwt.secret}") String secret,
                   @Value("${app.security.jwt.expiracion-minutos}") long expiracionMinutos) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITMO_MAC);
        this.expiracionSegundos = expiracionMinutos * 60;
    }

    public String generar(String username, String rol, Long idUsuario) {
        try {
            long ahora = Instant.now().getEpochSecond();
            Map<String, Object> header = new LinkedHashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("sub", username);
            payload.put("rol", rol);
            payload.put("idUsuario", idUsuario);
            payload.put("iat", ahora);
            payload.put("exp", ahora + expiracionSegundos);
            String headerB64 = ENCODER.encodeToString(MAPPER.writeValueAsBytes(header));
            String payloadB64 = ENCODER.encodeToString(MAPPER.writeValueAsBytes(payload));
            return headerB64 + "." + payloadB64 + "." + firmar(headerB64 + "." + payloadB64);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el token", e);
        }
    }

    public TokenClaims validar(String token) {
        String[] partes = token.split("\\.");
        if (partes.length != 3) {
            throw new JwtAuthException("El token tiene un formato inválido");
        }
        String firmaEsperada = firmar(partes[0] + "." + partes[1]);
        if (!constantTimeEquals(firmaEsperada, partes[2])) {
            throw new JwtAuthException("La firma del token no es válida");
        }
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> payload = MAPPER.readValue(DECODER.decode(partes[1]), Map.class);
            long exp = ((Number) payload.get("exp")).longValue();
            if (Instant.now().getEpochSecond() > exp) {
                throw new JwtAuthException("El token ha expirado");
            }
            String username = (String) payload.get("sub");
            String rol = (String) payload.get("rol");
            Long idUsuario = payload.get("idUsuario") == null ? null : ((Number) payload.get("idUsuario")).longValue();
            return new TokenClaims(username, rol, idUsuario, Instant.ofEpochSecond(exp));
        } catch (JwtAuthException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtAuthException("No se pudo leer el contenido del token");
        }
    }

    public Instant expiracionDe(String token) {
        return validar(token).expiracion();
    }

    private String firmar(String contenido) {
        try {
            Mac mac = Mac.getInstance(ALGORITMO_MAC);
            mac.init(secretKey);
            return ENCODER.encodeToString(mac.doFinal(contenido.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo firmar el token", e);
        }
    }

    private boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }

    public record TokenClaims(String username, String rol, Long idUsuario, Instant expiracion) {
    }
}
