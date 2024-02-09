package com.integrativeproyect.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.integrativeproyect.entity.User;
import com.integrativeproyect.repository.UserRepository;

@Component
public class JwtHandler {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	private UserRepository userRepository;
	
	public JwtHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public String signToken(User user) throws JWTCreationException{
		Date now = new Date();
		Date expiration = new Date(now.getTime() + 86400000);
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String signedToken = JWT.create()
								.withClaim("idUser", user.getId())
								.withIssuedAt(now)
								.withExpiresAt(expiration)
								.sign(algorithm);
		
		return signedToken;
	}
	
	public Authentication verifyToken(String token) throws JWTVerificationException{
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decoded = verifier.verify(token);
		User objUser;
		System.out.println("----> DECODED "+decoded.getClaim("idUser"));
		Optional<User> userOptional = userRepository.findById(Long.parseLong(decoded.getClaim("idUser").toString()));
		
		if(userOptional.isEmpty())
			throw new Error("User not found");

		objUser = userOptional.get();
		System.out.println("-----> "+objUser.getLastnameM());
		HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
		rolesAndAuthorities.add(new SimpleGrantedAuthority("ROLE_" + objUser.getRole()));
		rolesAndAuthorities.add(new SimpleGrantedAuthority("WRITE_READ"));
			
		return new UsernamePasswordAuthenticationToken(objUser, token, rolesAndAuthorities);
	}
	
}


