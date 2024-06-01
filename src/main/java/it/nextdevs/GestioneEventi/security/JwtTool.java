package it.nextdevs.GestioneEventi.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.excepion.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private long duration;


    public String createToken(User user) {
     return Jwts.builder().issuedAt(new Date(System.currentTimeMillis())).
             expiration(new Date(System.currentTimeMillis()+duration)).
             subject(String.valueOf(user.getId())).
             signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }


    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                    build().parse(token);
        }catch (Exception e){
            throw new NotFoundException("Error in authorization, re-login!");
        }

    }

    public int getIdFromToken(String token){
       return Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
               build().parseSignedClaims(token).getPayload().getSubject()) ;
    }

}
