package pe.idad.biblioteca.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// Clase utilitaria para manejo de JWT.
// Se encarga de generar, validar y extraer información del token.
@Component
public class JwtUtil {


    // Clave secreta utilizada para firmar el token JWT
    // Debe ser lo suficientemente larga para el algoritmo HS256
    private static final String SECRET = "bibliotecaSecretKeySuperSegura1234567890";

    // tiempo de vida del token (1 hora)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;


    // Genera la clave de firma a partir de la clave secreta
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Genera un token JWT con el email como sujeto
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el username (email) desde el token JWT
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida si el token JWT es correcto y no ha expirado
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}