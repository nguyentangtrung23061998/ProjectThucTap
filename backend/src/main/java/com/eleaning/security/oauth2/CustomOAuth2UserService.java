package com.eleaning.security.oauth2;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eleaning.bean.AuthProviderBean;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.exception.OAuth2AuthenticationProcessingException;
import com.eleaning.repository.IUserRepository;
import com.eleaning.security.oauth2.user.OAuth2UserInfo;
import com.eleaning.security.oauth2.user.OAuth2UserInfoFactory;
import com.eleaning.security.services.UserPrinciple;
import com.eleaning.service.IRoleService;
import com.nimbusds.oauth2.sdk.Role;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleService roleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService#
	 * loadUser(org.springframework.security.oauth2.client.userinfo.
	 * OAuth2UserRequest)
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<UserEntity> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        UserEntity user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProviderBean.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        System.out.println("oAuth2User.getAttributes(): " +oAuth2User.getAttributes());
        return UserPrinciple.build(user, oAuth2User.getAttributes());
    }

	private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(Calendar.getInstance().getTimeInMillis());
		userEntity.setProvider(AuthProviderBean.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		userEntity.setProviderId(oAuth2UserInfo.getId());
		userEntity.setUsername(oAuth2UserInfo.getName());
		userEntity.setEmail(oAuth2UserInfo.getEmail());
		userEntity.setImage(oAuth2UserInfo.getImageUrl());
		userEntity.setCreateddate(new Timestamp(System.currentTimeMillis()));
		RoleEntity roleStudent = roleService.findByRolename("ROLE_STUDENT");
		Set<RoleEntity> roles = new HashSet<RoleEntity>();
		roles.add(roleStudent);
		userEntity.setRole(roles);
		return userRepository.save(userEntity);
	}

	private UserEntity updateExistingUser(UserEntity existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setUsername(oAuth2UserInfo.getName());
		existingUser.setImage(oAuth2UserInfo.getImageUrl());
		return userRepository.save(existingUser);
	}
}
