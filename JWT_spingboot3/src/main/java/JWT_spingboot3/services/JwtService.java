package JWT_spingboot3.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;                              // <-- javax.crypto
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey jwtSigningKey;

    @Value("${security.jwt.expiration-minutes:60}")
    private long expMinutes;

    /* ============ Generate ============ */
    public String generateToken(UserDetails user) {
        return generateToken(Map.of(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
                .signWith(jwtSigningKey)                   // HS256 mặc định
                .compact();
    }

    /* ============ Extract ============ */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtSigningKey)                 // verify chữ ký
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* ============ Validate ============ */
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return username != null && username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /* ============ For controller/UI ============ */
    /** Thời hạn token (phút) – để chỗ khác hiển thị nếu cần */
    public long getExpirationTime() {
        return expMinutes;
    }
}
