package com.example.backweb.common.util;

import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
public class JwtUtil {

    /**
     * 加密
     *
     * @param username
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String username, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("1sf12sds21ie1inecs078j");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * 解密
     *
     * @param jwt
     */
    public static Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("1sf12sds21ie1inecs078j"))
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        }catch (ExpiredJwtException e){
            return null;
        }

    }

    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
