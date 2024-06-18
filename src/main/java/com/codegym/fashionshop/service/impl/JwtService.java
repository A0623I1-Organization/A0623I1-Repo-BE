package com.codegym.fashionshop.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

//    Phương thức này trích xuất tên người dùng (username) từ token JWT. Nó gọi phương thức extractClaims để lấy các claims từ token và lấy subject (username) từ các claims này.
    public String extractUsername(String token) {
        try {
            return extractClaims(token, Claims::getSubject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

//Phương thức này trích xuất các claims từ token bằng cách sử dụng một Function để xử lý các claims. Nó gọi phương thức extarctAllClaims để lấy tất cả các claims từ token và sau đó áp dụng claimsResolver để trích xuất thông tin cụ thể.
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        try {
            final Claims claims = extarctAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

//    Phương thức này tạo một token JWT mới cho người dùng dựa trên thông tin của họ. Nó gọi phương thức generateToken với một bản đồ rỗng các claims bổ sung và chi tiết người dùng.
    public String generateToken(UserDetails userDetails){
        Map<String, String> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        // Convert danh sách các vai trò (roles) thành chuỗi
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("roles", roles);
        return generateToken(claims, userDetails);
    }

//    Phương thức này tạo một token JWT mới với các claims bổ sung và chi tiết người dùng. Nó xây dựng token bằng cách thiết lập các claims, subject (username), thời gian phát hành, thời gian hết hạn, và sử dụng khóa bí mật để ký token.
    public String generateToken(
            Map<String, String> extraClaims,
            UserDetails userDetails
    ) {
        try {
            return Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

//    Phương thức này kiểm tra xem token có hợp lệ hay không bằng cách so sánh username trích xuất từ token với username của người dùng và kiểm tra xem token có hết hạn hay không.
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

//Phương thức này kiểm tra xem token đã hết hạn hay chưa bằng cách trích xuất ngày hết hạn từ token và so sánh với ngày hiện tại.
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

//Phương thức này trích xuất ngày hết hạn từ token bằng cách sử dụng extractClaims và Claims::getExpiration.
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

//     Phương thức này trích xuất tất cả các claims từ token bằng cách sử dụng khóa bí mật để giải mã token.
    private Claims extarctAllClaims(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

//     Phương thức này tạo ra một đối tượng Key sử dụng khóa bí mật đã được mã hóa Base64. Nó giải mã khóa bí mật và sử dụng Keys.hmacShaKeyFor để tạo khóa mã hóa HMAC.
    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}