package project.by.skillintern.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private final long ACCESS_TOKEN_VALIDITY = 86_400_000; // 1 день

    /**
     * Извлекает имя пользователя из токена.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлекает дату истечения токена.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает конкретный claim из токена.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Генерация access и refresh токенов.
     */
    public Map<String, String> generateToken(String userName, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String accessToken = createToken(claims, userName, ACCESS_TOKEN_VALIDITY, "access");
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        return tokens;
    }


    /**
     * Проверка валидности access токена.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        String tokenType = extractClaim(token, claims -> claims.get("typ", String.class));
        String tokenRole = extractClaim(token, claims -> claims.get("role", String.class));

        boolean hasRequiredRole = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(tokenRole));

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token)
                && "access".equals(tokenType) && hasRequiredRole;
    }

    /**
     * Проверяет, истёк ли токен.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Создаёт JWT токен.
     */
    private String createToken(Map<String, Object> claims, String userName, long validity, String type) {
        claims.put("typ", type); // Тип токена (access или refresh)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName) // Устанавливаем имя пользователя как subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Время создания токена
                .setExpiration(new Date(System.currentTimeMillis() + validity)) // Время истечения
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Подпись токена
                .compact();
    }

    /**
     * Извлекает все claims из токена.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey()) // Устанавливаем секретный ключ
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }

    /**
     * Генерирует ключ для подписи токена на основе секрета.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Конфигурация PasswordEncoder для шифрования паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
