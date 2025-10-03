package JWT_spingboot3.configs;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;               // <-- dùng javax.crypto
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret-base64}")
    private String secretBase64;            // chuỗi Base64 đủ dài (>=256 bit)

    @Bean
    public SecretKey jwtSigningKey() {
        // Tạo HMAC key từ Base64 (dùng cho HS256)
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }
}
