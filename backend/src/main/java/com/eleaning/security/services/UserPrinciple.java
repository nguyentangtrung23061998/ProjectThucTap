package com.eleaning.security.services;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.eleaning.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrinciple implements OAuth2User, UserDetails {

	private Long id;
	private String email;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	
	public UserPrinciple(Long id, String email, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.attributes = attributes;
	}
	
	public static UserPrinciple build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRole()
					.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
//		List<GrantedAuthority> authorities = Collections.
//                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new UserPrinciple(user.getId(), user.getEmail(), user.getFullname(), user.getPassword(), authorities);
	}
	
	public static UserPrinciple build(UserEntity user,Map<String, Object> attributes) {
		UserPrinciple userPrinciple = UserPrinciple.build(user);
		userPrinciple.setAttributes(attributes);
		return userPrinciple;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
