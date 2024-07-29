package com.kennedy.shopkeeper_plus.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

	@Value("$app.jwtSecret")
	private String jwtSecret;

	@Value("$app.jwtExpirationInMs")
	private int jwtExpirationInMs;

	public String generateToken(UUID userId) {
		var algorithm = Algorithm.HMAC256(jwtSecret);

		return JWT.create()
				       .withClaim("userId", userId.toString())
				       .withIssuedAt(new Date())
				       .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				       .sign(algorithm);
	}


}
