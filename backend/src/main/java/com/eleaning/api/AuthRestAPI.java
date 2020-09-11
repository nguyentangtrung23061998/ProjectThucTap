package com.eleaning.api;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eleaning.bean.AuthProviderBean;
import com.eleaning.bean.LoginBean;
import com.eleaning.bean.MapBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.SignUpBean;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.security.jwt.JwtProvider;
import com.eleaning.service.IRoleService;
import com.eleaning.service.IUserService;
import com.eleaning.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPI {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder encode;

	private static String jwtType = "Bearer ";

	@PostMapping("/signin")
	public ResponseEntity<ResponseBean> authenicateUser(@RequestBody LoginBean loginBean) {
		ResponseBean responseBean = new ResponseBean();
		
		try {
			loginBean.setEmail(Util.trim(loginBean.getEmail()));
			loginBean.setPassword(Util.trim(loginBean.getPassword()));

			if (loginBean.getEmail() == null || loginBean.getPassword() == null) {
				responseBean.setEnterAllRequiredFields();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			}
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginBean.getEmail(), loginBean.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserEntity user = userService.findByEmail(loginBean.getEmail());
			
			String jwt = jwtProvider.generateJwtToken(authentication);
			Authentication roleAuthentication = SecurityContextHolder.getContext().getAuthentication();
			List list = (List) roleAuthentication.getAuthorities();

			user.setToken(jwtType + jwt);
			userService.save(user);
			MapBean map = new MapBean();
			map.put("id", user.getId());
			map.put("username", user.getUsername());
			map.put("email", user.getEmail());
			map.put("fullname", user.getFullname());
			map.put("image",user.getImage());
			map.put("token", user.getToken());
			map.put("role",list.get(0));
			map.put("provider",user.getProvider());

			responseBean.setData(map.getAll());
			responseBean.setSuccess();

			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			
		} catch (AuthenticationException e) {
			System.out.println("ex: " + e.getMessage());
			responseBean.setLoginFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<ResponseBean> registerUser(@RequestBody SignUpBean signupBean) {
		ResponseBean responseBean = new ResponseBean();
		
		if (userService.existsByUsername(signupBean.getUsername())) {
			responseBean.setIsExisting();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		if (userService.existsByEmail(signupBean.getEmail())) {
			responseBean.setEmailIsExisting();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
		if (signupBean.getUsername() == null || signupBean.getPassword() == null || signupBean.getRole().size() < 0
				|| signupBean.getFullname() == null || signupBean.getEmail() == null) {
			responseBean.setEnterAllRequiredFields();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		// create user's account
		Long id = Calendar.getInstance().getTimeInMillis();
		String encodePasswod = encode.encode(signupBean.getPassword());
		UserEntity user = new UserEntity(id, signupBean.getUsername(), encodePasswod, signupBean.getEmail(), true,	"",
				signupBean.getFullname(), "",AuthProviderBean.local,"", new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()), "", "");

		Set<String> strRoles = signupBean.getRole();
		Set<RoleEntity> roles = new HashSet<RoleEntity>();
		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				RoleEntity roleAdmin = roleService.findByRolename("ROLE_ADMIN");
				if (roleAdmin == null) {
					responseBean.setRoleUserNotFound();
				}
				roles.add(roleAdmin);
				break;

			case "STUDENT":
				RoleEntity roleStudent = roleService.findByRolename("ROLE_STUDENT");
				if (roleStudent == null) {
					responseBean.setRoleUserNotFound();
				} else {
					roles.add(roleStudent);
				}
				break;
			case "TEACHER":
				RoleEntity roleTeacher = roleService.findByRolename("ROLE_TEACHER");
				if (roleTeacher == null) {
					responseBean.setRoleUserNotFound();
				} else {
					roles.add(roleTeacher);
				}
				break;
			default:
				responseBean.setRoleUserNotFound();
				break;
			}
		});

		user.setRoles(roles);
		userService.save(user);

		MapBean map = new MapBean();
		map.put("username", user.getUsername());
		map.put("email", user.getEmail());
		map.put("fullname", user.getFullname());
		map.put("provider", user.getProvider());

		responseBean.setData(map);
		responseBean.setInsertSuccess();
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

}
