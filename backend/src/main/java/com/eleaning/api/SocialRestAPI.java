package com.eleaning.api;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.UserBean;
import com.eleaning.conveter.UserConverter;
import com.eleaning.entity.UserEntity;
import com.eleaning.security.CurrentUser;
import com.eleaning.security.services.UserPrinciple;
import com.eleaning.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SocialRestAPI {
	private static final Logger logger = LoggerFactory.getLogger(SocialRestAPI.class);
//	@Autowired
//    private IUserRepository userRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserConverter userConverter;
	
//	@GetMapping("/user/me")
//	public UserEntity getCurrentUser(@CurrentUser UserPrinciple userPrincipal) {
//		System.out.println("aaaaaaaaaa");
//		return userRepository.findById(userPrincipal.getId())
//				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
//	}
	@GetMapping("/user/me")
	public ResponseEntity<ResponseBean> getCurrentUser(@CurrentUser UserPrinciple userPrincipal, HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();
		try {
			UserEntity userEntity = userService.findUserByid(userPrincipal.getId());
			UserBean userBean = userConverter.convertBean(userEntity);
			System.out.println(userBean.getEmail());
			responseBean.setSuccess();
			responseBean.setData(userBean);
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		responseBean.setBadRequest();
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
}
