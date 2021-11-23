package com.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.demo.constant.Constants;
import com.demo.entity.Customer;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

//    @Value("${config.security.jwt.secret-key}")
    String secretKey ="";

//    @Value("${config.security.jwt.secret-key.one-day}")
    String secretKeyOneDay ="";

//    @Value("${config.security.jwt.secret-key.refresh-token}")
    String secretKeyRefreshToken ="";

//    @Value("${jwt.reset.token.expiration-in-seconds}")
    long resetTokenExpiration =1l;

//    @Value("${jwt.login.token.expiration-in-seconds}")
    long loginTokenExpiration=1l;

//    @Value("${jwt.refresh.token.expiration-in-seconds}")
    long loginRefreshTokenExpiration=1l;

    @Autowired
    HttpServletRequest httpRequest;

    // Create SignatureAlgorithm
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    byte[] apiKeySecretBytes;

    byte[] apiKeySecretOneDayBytes;

    byte[] apiKeySecretRefreshTokenBytes;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        // Parse secret key to bytes
        apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        // Parse secret key to bytes
        apiKeySecretOneDayBytes = DatatypeConverter.parseBase64Binary(secretKeyOneDay);
        // Parse secret key refresh token to bytes
        apiKeySecretRefreshTokenBytes = DatatypeConverter.parseBase64Binary(secretKeyRefreshToken);
    }

    /**
     * This method generates access token
     * @param user user detail
     * @return JWT Access Token
     */
    public String createAccessToken(Customer cust) {
        //Maps roles in the SQL Database onto the JWT and adds validity
        Claims claims = Jwts.claims().setSubject(Constants.JWT_ACCESS_TOKEN);
        // Set email into JWT request body
        claims.put(Constants.JWT_EMAIL, cust.getEmail());
        // Set firstname into JWT request body
        claims.put(Constants.JWT_FIRSTNAME, cust.getFirstname());
        // Set lastname into JWT request body
        claims.put(Constants.JWT_LASTNAME, cust.getLastname());
        // Set purchase_limit into JWT request body
        claims.put(Constants.JWT_PURCHASE_LIMIT, "");
        // Set org_logo into JWT request body
        claims.put(Constants.JWT_ORG_LOG, "");
        // Set store_name into JWT request body
        claims.put(Constants.JWT_STORE_NAME, cust.getStoreName());
        // Set storeDisplayName into JWT request body
        claims.put(Constants.JWT_STORE_DISP_NAME, "");
        // Set org_name into JWT request body
        claims.put(Constants.JWT_ORG_NAME, cust.getOrgName());
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + getJWExpiryInMillis(loginTokenExpiration));
        // Create signingKey
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(getSigningKey(), signatureAlgorithm).compact();
    }

    /**
     * This method generates refresh token
     * @param user user detail
     * @return JWT Refresh Token
     */
    public String createRefreshToken(Customer cust) {
        //Maps roles in the SQL Database onto the JWT and adds validity
        Claims claims = Jwts.claims().setSubject(Constants.JWT_REFRESH_TOKEN);
        // Set user Id into JWT request body
        claims.put(Constants.JWT_EMAIL, cust.getEmail());
        Date now = new Date();
        // Create a one month validity
        Date validity = new Date(now.getTime() + getJWExpiryInMillis(loginRefreshTokenExpiration));
        // Create signingKey
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(getSigningKeyForRefreshToken(), signatureAlgorithm).compact();
    }

    public String getUsername (String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken (HttpServletRequest req)
    {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.JWT_INVALID_EXPIRED, e);
        }
    }

    /**
     * This method is used to validate requested refresh token
     * @param token JWT Refresh Token
     */
    public void validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKeyForRefreshToken()).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.JWT_INVALID_EXPIRED, e);
        }
    }

    /**
     * Extract value from JWT token by passing Key
     *
     * @param keyName (user_id/company_id)
     * @return extracted value
     */
    public Long getValueFromJWTByKey(String keyName) {
        try {
            // Fetch token from httpServletRequest
            String token = resolveToken(httpRequest);
            // Check and validate token
            if (token != null) {
                // Validate Token
                validateToken(token);
                // Extract claims from token
                Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
                // extract key id from token
                return Long.valueOf(claims.get(keyName).toString());
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_INVALID_EXPIRED, ex);
        }
        return null;
    }

    /**
     * Create Key
     *
     * @return Key
     */
    private Key getSigningKey() {
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    /**
     * Create Key for one day token
     *
     * @return Key
     */
    private Key getSigningKeyForOneDay() {
        return new SecretKeySpec(apiKeySecretOneDayBytes, signatureAlgorithm.getJcaName());
    }

    /**
     * Create Key for Refresh token
     *
     * @return Key
     */
    private Key getSigningKeyForRefreshToken() {
        return new SecretKeySpec(apiKeySecretRefreshTokenBytes, signatureAlgorithm.getJcaName());
    }

    /**
     * This method is used to validate requested token
     *
     * @param token JWT token
     * @return If valid then true else false
     */
    public boolean validateOneDayToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKeyForOneDay()).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.JWT_INVALID_EXPIRED, e);
        }
        return true;
    }

    private long getJWExpiryInMillis(long expiration) {
        return expiration * 1000;
    }
}
