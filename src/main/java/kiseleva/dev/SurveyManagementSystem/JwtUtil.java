package kiseleva.dev.SurveyManagementSystem;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kiseleva.dev.SurveyManagementSystem.entities.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration-ms}")
    private long accessExpirationMs;

    @Value("${jwt.refresh.expiration-ms}")
    private long refreshExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserDetails user) {
        return generateToken(user, TokenType.ACCESS, accessExpirationMs);
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(user, TokenType.REFRESH, refreshExpirationMs);
    }

    private String generateToken(UserDetails user, TokenType type, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("tokenType", type.name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValid(String token, TokenType expectedType) {
        try {
            Claims claims = parseAllClaims(token);

            String tokenTypeStr = claims.get("tokenType", String.class);
            if (tokenTypeStr == null || !tokenTypeStr.equals(expectedType.name())) {
                return false;
            }

            return !isExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return parseAllClaims(token).getSubject();
    }

    private Claims parseAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
