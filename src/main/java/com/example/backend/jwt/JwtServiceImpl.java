package com.example.backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{
    @Override
    public Token generateToken(UserDetails userDetails) {
        Token token = new Token();
        token.setCode(Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.JWT_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact());
        return token;
    }
    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(JwtConstant.JWT_KEY);
        return Keys.hmacShaKeyFor(key);
    }
    @Override
    public Token generateRefreshToken(Map<String, Objects> extraClaims, UserDetails userDetails) {
        Token token = new Token();
        token.setCode(Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.JWT_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact());
        return token;
    }

    private Claims extraToken(String token){
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
// DONE:           chữ ký của JWT không hợp lệ.
            throw new SignatureException(e.getMessage());
        }
    }
    private <T> T extraClaim(String token, Function<Claims,T> ClaimsResolve){
        final Claims claims = extraToken(token);
        return ClaimsResolve.apply(claims);
    }
    @Override
    public String extraUsername(String token) {
        return extraClaim(token,Claims::getSubject);
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extraUsername(token);
        return (username.equals(userDetails.getUsername()) & !isTokenExpiration(token));
    }

    private boolean isTokenExpiration(String token) {
        return extraClaim(token,Claims::getExpiration).before(new Date());
    }
}
