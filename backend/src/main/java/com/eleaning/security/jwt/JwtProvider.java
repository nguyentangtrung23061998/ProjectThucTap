package com.eleaning.security.jwt;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eleaning.config.AppProperties;
import com.eleaning.entity.RoleEntity;
import com.eleaning.security.services.UserPrinciple;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	private AppProperties appProperties;

    public JwtProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

	
//	@Value("${elearning.app.jwtSecret}")
//	private String jwtSecret;
//	
//	@Value("${elearning.app.jwtExpiration}")
//	private int jwtExpiration;
	
	public String generateJwtToken(Authentication authentication) {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		 return Jwts.builder()
	                .setSubject((userPrinciple.getUsername()))
	                .setIssuedAt(new Date())
	                .setExpiration(new Date((new Date()).getTime() + appProperties.getAuth().getTokenExpirationMsec()))
	                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
	                .compact();
	}
	
	public String createToken(String username, Set<RoleEntity> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles",roles);
		Date now = new Date();
		Date validatity = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validatity)
					.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
					.compact();
		
	}
	
	public boolean validateJwtToken(final String authToken,HttpServletRequest httpServletRequest) {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
        	 httpServletRequest.setAttribute("expired",e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
		return false;
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token)
				.getBody().getSubject();
	}
}
