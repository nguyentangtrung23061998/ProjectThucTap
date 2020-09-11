package com.eleaning.security.oauth2.user;

import java.util.Map;

import com.eleaning.bean.AuthProviderBean;
import com.eleaning.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if(registrationId.equals(AuthProviderBean.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		}else if(registrationId.equals(AuthProviderBean.facebook.toString())){
			return new FacebookOAuth2UserInfo(attributes);
		}else {
			throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
