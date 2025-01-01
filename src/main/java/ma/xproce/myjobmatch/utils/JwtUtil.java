package ma.xproce.myjobmatch.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    //get Sign in Key

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT token validity duration (2 hours)
    private static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60 * 1000; // 2 hours

    // Method to generate a JWT token
    //maybe it should take AUthentication auth as an input?
    public String generateToken(UserDetails userDetails) {
        //return generateToken(new HashMap<>(), userDetails);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Set username as subject
                .claim("role", userDetails.getAuthorities())  // Add roles as a claim
                .setIssuedAt(new Date())  // Set issue date
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))  // Set expiration date
                .signWith(SECRET_KEY)  // Sign with the secret key
                .compact();
    }
    public String generateToken2(Authentication authentication) {
        // Get the username and authorities (roles) from the authenticated user
        String username = authentication.getName();
        String roles = authentication.getAuthorities().toString();

        // Generate the JWT token
        return Jwts.builder()
                .setSubject(username) // Subject is typically the username (or email)
                .claim("role", roles) // You can add additional claims, e.g., user roles
                .setIssuedAt(new Date()) // The time the token was created
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Set expiration time
                .signWith(SECRET_KEY) // Sign the token with HS256 algorithm and secret key
                .compact(); // Compact and return the token
    }

    // Method to extract the username from the JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Method to extract a specific claim (role in this case) from the JWT token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to extract a claim from the token based on the claim resolver function
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    // Method to validate the JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenExpired(token) && isTokenSignatureValid(token));
    }

    private boolean isTokenSignatureValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)  // Ensure secretKey is defined in your class
                    .parseClaimsJws(token);
            return true;  // If no exception, signature is valid
        } catch (JwtException e) {
            return false;  // Invalid signature or token format
        }
    }

    // Method to check if the JWT token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to extract expiration date from the JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Method to check if the JWT token is expired
    //public boolean isTokenExpired(Claims claims) {
   //     return claims.getExpiration().before(new Date());
  //  }

    // Helper functional interface to extract claims
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
