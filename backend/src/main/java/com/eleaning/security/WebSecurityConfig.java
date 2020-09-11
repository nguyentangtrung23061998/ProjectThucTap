package com.eleaning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eleaning.security.jwt.JwtAuthEntryPoint;
import com.eleaning.security.jwt.JwtAuthTokenFilter;
import com.eleaning.security.oauth2.CustomOAuth2UserService;
import com.eleaning.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.eleaning.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.eleaning.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.eleaning.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl customUserDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthTokenFilter tokenAuthenticationFilter() {
		return new JwtAuthTokenFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
         .cors()
             .and()
         .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
         .csrf()
             .disable()
         .formLogin()
             .disable()
         .httpBasic()
             .disable()
         .exceptionHandling()
             .authenticationEntryPoint(new JwtAuthEntryPoint())
             .and()
         .authorizeRequests()
             .antMatchers("/",
                 "/error",
                 "/favicon.ico",
                 "/**/*.png",
                 "/**/*.gif",
                 "/**/*.svg",
                 "/**/*.jpg",
                 "/**/*.html",
                 "/**/*.css",
                 "/**/*.js")
                 .permitAll()
             .antMatchers("/api/auth/**", 
            		 		"/oauth2/**",
            		 		"/upload/**",
            		 		"/api/all-courses",
            		 		"/api/users/teacher",
            		 		"/api/courses/**",
            		 		"/api/questions/**")
                 .permitAll()
             .anyRequest()
                 .authenticated()
             .and()
         .oauth2Login()
             .authorizationEndpoint()
                 .baseUri("/oauth2/authorize")
                 .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                 .and()
             .redirectionEndpoint()
                 .baseUri("/oauth2/callback/*")
                 .and()
             .userInfoEndpoint()
                 .userService(customOAuth2UserService)
                 .and()
             .successHandler(oAuth2AuthenticationSuccessHandler)
             .failureHandler(oAuth2AuthenticationFailureHandler);
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
