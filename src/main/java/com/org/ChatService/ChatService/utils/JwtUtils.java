package com.org.ChatService.ChatService.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class JwtUtils
{

    @Value("${app.secret}")
    private String secret;

    //7. Validate token username and request username + expiry date:
    public boolean validateToken(String token , String username)
    {
        String userNameInToken = getUserName(token);
        return (userNameInToken.equals(username) && !isTokenExpired(token));
    }

    //6. Check current and Exp Date:
    private boolean isTokenExpired(String token)
    {
        final Date expirationDate = getExpDate(token);
        return expirationDate.before(new Date());
    }


    //5. Generate Token with empty claims
    public String generateToken(String username)
    {
        Map<String,Object> claims =  new HashMap<>();
        return generateJwtToken(claims , username);
    }

    //4. Read UserName

    public String getUserName(String token)
    {
        return getClaims(token).getSubject();
    }

    //3. Read ExpDate

    public Date getExpDate(String token)
    {
        return getClaims(token).getExpiration();
    }

    //2. Read Claims
    private Claims getClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //1. Generate Token
    private String generateJwtToken(Map<String , Object> claims , String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("CHATAPPLICATION")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS256 , secret)
                .compact();
    }
}
