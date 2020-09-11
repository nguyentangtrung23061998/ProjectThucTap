package com.eleaning.api;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eleaning.bean.ChangePassword;
import com.eleaning.bean.CourseBean;
import com.eleaning.bean.CourseUserBean;
import com.eleaning.bean.MapBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.RoleNameBean;
import com.eleaning.bean.UserAboutCourseBean;
import com.eleaning.bean.UserBean;
import com.eleaning.conveter.CourseConverter;
import com.eleaning.conveter.UserConverter;
import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.exception.ResourceNotFoundException;
import com.eleaning.security.CurrentUser;
import com.eleaning.security.jwt.JwtProvider;
import com.eleaning.security.services.UserPrinciple;
import com.eleaning.service.ICourseService;
import com.eleaning.service.IRoleService;
import com.eleaning.service.IUserService;
import com.eleaning.util.Constant;
import com.eleaning.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestAPI {

	private static final Logger logger = LoggerFactory.getLogger(UserRestAPI.class);

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private CourseConverter courseConverter;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvide;

	public boolean checkRole(List list) {
		for (int i = 0; i < list.size(); i++) {
			String role = String.valueOf(list.get(i));
			RoleNameBean strRoleStudent = RoleNameBean.ROLE_STUDENT;
			RoleNameBean strRoleTeacher = RoleNameBean.ROLE_TEACHER;
			String roleStudent = strRoleStudent.getValue();
			String roleTeacher = strRoleTeacher.getValue();
			System.out.println("role: " + role);
			if (role.equals(roleStudent) || role.equals(roleTeacher)) {
				return false;
			}
		}
		return true;
	}
	
	@GetMapping("/me")
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
	
	@GetMapping("")
	private ResponseEntity<ResponseBean> getUsers() {
		ResponseBean responseBean = new ResponseBean();
		UserBean userBean = new UserBean();
		try {
		
			List<UserEntity> users = userService.getUsers();
			List<UserBean> listUser = new ArrayList<UserBean>();
			for (UserEntity user : users) {
				userBean = userConverter.convertBean(user);
				Set<RoleEntity> userData = user.getRole();
				Iterator<RoleEntity> role = userData.iterator();
				userBean.setRole(role.next().getRolename());
				listUser.add(userBean);
			}
			responseBean.setData(listUser);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@GetMapping("/admin")
	private ResponseEntity<ResponseBean> getUsersByAdmin() {
		ResponseBean responseBean = new ResponseBean();
		UserBean userBean = new UserBean();
		try {
			List<UserEntity> data = userService.getUsersByAdmin();
			responseBean.setData(data);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}
	
	@GetMapping("/teacher")
	private ResponseEntity<ResponseBean> getUsersByTeacher() {
		ResponseBean responseBean = new ResponseBean();
		UserBean userBean = new UserBean();
		try {
			List<UserEntity> data = userService.getUsersByTeacher();
			responseBean.setData(data);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}
	
	@GetMapping("/student")
	private ResponseEntity<ResponseBean> getUsersByStudent() {
		ResponseBean responseBean = new ResponseBean();
		UserBean userBean = new UserBean();
		try {
			List<UserEntity> data = userService.getUsersByStudent();
			responseBean.setData(data);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<ResponseBean> getUserById(@PathVariable Long id) {
		ResponseBean responseBean = new ResponseBean();
		try {
			UserEntity user = userService.findUserByid(id);
			if (user != null) {
				responseBean.setData(user);
				responseBean.setSuccess();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			} else {
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@GetMapping("/{id}/courses")
	private ResponseEntity<ResponseBean> getCourseByuserId(@PathVariable Long id) {
		ResponseBean responseBean = new ResponseBean();
		MapBean mapBean = new MapBean();
		try {
			List<CourseEntity> courses = courseService.getCoursesByUserId(id);
			CourseUserBean courseUserBean = new CourseUserBean();
			List<CourseUserBean> courseUserBeanData = new ArrayList<CourseUserBean>();
			for (CourseEntity courseEntity : courses) {
				UserEntity userEntity = userService.findUserByid(courseEntity.getUser().getId());
				Iterable<RoleEntity> iterable = userEntity.getRole();
				UserAboutCourseBean userBean = userConverter.convertUserAboutBean(userEntity);
				userBean.setRole(iterable.iterator().next().getRolename());

				CourseBean courseBean = courseConverter.convertBean(courseEntity);
				courseBean.setTotalStudentEnroll(courseEntity.getUsers().size());
				courseUserBean = new CourseUserBean(courseBean, userBean);
				courseUserBeanData.add(courseUserBean);
			}
			responseBean.setData(courseUserBeanData);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@PostMapping("/upload/{id}")
	private ResponseEntity<ResponseBean> uploadUsers(@PathVariable long id, @RequestParam("file") MultipartFile file)
			throws IOException {
		UserEntity user = userService.findUserByid(id);
		ResponseBean responseBean = new ResponseBean();
		try {
			if (user != null) {
				boolean checkUpload = Util.upload(file);
				if (checkUpload) {
					user.setImage(file.getOriginalFilename());
					userService.save(user);
					responseBean.setData(user);
					responseBean.setSuccess();
					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				} else {
					responseBean.setFailUpload();
					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PutMapping("/update_profile/{id}")
	private ResponseEntity<ResponseBean> updateUser(@PathVariable long id, @RequestBody UserBean userBean) {
		ResponseBean responseBean = new ResponseBean();
		try {
			Set<RoleEntity> roles = new HashSet<RoleEntity>();
			UserEntity user = userService.findUserByid(id);
			RoleEntity role = roleService.findByRolename(userBean.getRole());
			
//			Iterator<RoleEntity> roleIterator = user.getRole().iterator();
//			String role = "";
//			while (roleIterator.hasNext()) {
//				role = roleIterator.next().getRolename();
//				break;
//			}
					;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			List list = (List) authentication.getAuthorities();

			String roleSystem = String.valueOf(list.get(0));
			boolean check = checkRole(list);
			
//			user.setEmail(userBean.getEmail());
			user.setUsername(userBean.getUsername());
			user.setFullname(userBean.getFullname());
			if (check) {

				roles.add(role);
				user.setRole(roles);

				UserEntity userEntity = userService.save(user);
				UserBean userOutput = userConverter.convertBean(userEntity);
				userOutput.setImage(user.getImage());
				userOutput.setRole(userBean.getRole());

				responseBean.setData(userOutput);
				responseBean.setSuccess();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			} else {
				if (!userBean.getRole().equals(roleSystem)) {
					MapBean map = new MapBean();
					map.put("You are not admin", "user don't update role's user");
					responseBean.setData(map.getAll());
					responseBean.setBadRequest();

					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				} else {
					
					roles.add(role);
					user.setRole(roles);
					UserEntity userEntity = userService.save(user);
					UserBean userOutput = userConverter.convertBean(userEntity);
					userOutput.setImage(user.getImage());
					userOutput.setRole(userBean.getRole());

					responseBean.setData(userOutput);
					responseBean.setSuccess();
					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@PutMapping("/change_password")
	private ResponseEntity<ResponseBean> updateUserPassword(@RequestBody ChangePassword changePassword,
			Principal principal) {
		ResponseBean responseBean = new ResponseBean();
		try {
			System.out.println("principal: " + principal.getName());
			UserEntity user = userService.findByEmail(principal.getName());
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), changePassword.getCurrentPassword()));

//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(user.getUsername(), changePassword.getCurrentPassword()));

			if (changePassword.getNewPassword().equals(changePassword.getCurrentPassword())) {
				responseBean.setPasswordSame();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			}

			user.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));

			UserEntity userEntity = userService.save(user);
			UserBean userOutput = userConverter.convertBean(userEntity);

			responseBean.setData(userOutput);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (AuthenticationException e) {
			logger.error(e.getMessage());
			responseBean.setPasswordFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<ResponseBean> deleteUser(@PathVariable long id) {
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);
		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		userService.delete(id);
		responseBean.setData("{}");
		responseBean.setSuccess();
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@GetMapping("/forgot/email")
	private ResponseEntity<ResponseBean> forgotUser(@PathParam("email") String email) {
		System.out.println("email: " + email);
		ResponseBean responseBean = new ResponseBean();
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			UserEntity user = userService.findByEmail(email);
			System.out.println("username: " + user.getUsername());
			String token = jwtProvide.createToken(user.getUsername(), user.getRole());
//			String token = null;
			System.out.println("token:" + token);
			String htmlMsg = "<p style='color:black;font-size:18px;'>"
					+ "http://localhost:4200/users/changePassword?token=" + token + "</p>";

			message.setContent(htmlMsg, "text/html");
			helper.setTo(user.getEmail());
			helper.setSubject("Reset password");

			emailSender.send(message);
			responseBean.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
}
