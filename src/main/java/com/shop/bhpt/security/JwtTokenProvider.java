package com.shop.bhpt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shop.bhpt.entities.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationInMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Tạo JWT từ thông tin người dùng
     *
     * @param username Tên người dùng
     * @param role Vai trò của người dùng
     * @return JWT
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getUsername()) // Thêm username vào subject
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .claim("id", user.getId())       
                .setIssuedAt(now)               // Thời điểm tạo token
                .setExpiration(expiryDate)      // Thời điểm hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký token
                .compact();
    }


    /**
     * Lấy username từ JWT
     *
     * @param token JWT
     * @return Username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Lấy vai trò từ JWT
     *
     * @param token JWT
     * @return Vai trò
     */
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    /**
     * Xác thực JWT
     *
     * @param token JWT
     * @return True nếu hợp lệ, False nếu không
     */
    @SuppressWarnings("deprecation")
	public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.err.println("JWT hết hạn: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.err.println("JWT không được hỗ trợ: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.err.println("JWT không hợp lệ: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.err.println("Chữ ký không hợp lệ: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT rỗng: " + ex.getMessage());
        }
        return false;
    }
}
