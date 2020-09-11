package com.eleaning.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.eleaning.entity.LectureEntity;
import com.eleaning.entity.MediaEntity;
import com.eleaning.entity.QuestionsBankEntity;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.service.ICourseService;
import com.eleaning.service.IMediaService;
import com.eleaning.service.IUserService;
import com.eleaning.util.Constant;
import com.eleaning.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CourseRestAPI {
	private static final Logger logger = LoggerFactory.getLogger(CourseRestAPI.class);

//	private static final HashSet LectureEntity = null;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private IUserService userService;

	@Autowired
	private CourseConverter courseConverter;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private IMediaService mediaService;

	@Autowired
	ServletContext context;

	public boolean checkRole(List list) {
		for (int i = 0; i < list.size(); i++) {
			String role = String.valueOf(list.get(i));
			RoleNameBean strRole = RoleNameBean.ROLE_STUDENT;
			String roleStudent = strRole.getValue();
			if (role.equals(roleStudent))
				return false;
		}
		return true;
	}

//	public String getRole()

	@GetMapping("/all-courses")
	private ResponseEntity<ResponseBean> getCourse(HttpServletRequest request) {
		
		String authHeader = request.getHeader("Authorization");
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			List list = (List) authentication.getAuthorities();
			String role = String.valueOf(list.get(0));

			List<CourseEntity> courses = courseService.getAll();

			CourseUserBean courseUserBean = new CourseUserBean();
			List<CourseUserBean> courseUserBeanData = new ArrayList<CourseUserBean>();

			RoleNameBean strRoleAdmin = RoleNameBean.ROLE_ADMIN;
			String roleAdmin = strRoleAdmin.getValue();

			for (CourseEntity courseEntity : courses) {
				if (role.equals(roleAdmin)) {
					// List<QuestionsBankEntity> questionBanks =
					// courseEntity.getLectures().get(0).getQuestionsBanks();
					List<LectureEntity> lectures = courseEntity.getLectures();
					Set<LectureEntity> set = new HashSet<LectureEntity>(lectures);
					List<LectureEntity> listWithoutDuplicateElements = new ArrayList<LectureEntity>(set);

					UserEntity userEntity = userService.findUserByid(courseEntity.getUser().getId());
					Iterable<RoleEntity> iterable = userEntity.getRole();
					UserAboutCourseBean userBean = userConverter.convertUserAboutBean(userEntity);
					userBean.setRole(iterable.iterator().next().getRolename());
					CourseBean courseBean = courseConverter.convertBean(courseEntity);
					courseBean.setTotalStudentEnroll(courseEntity.getUsers().size());

					courseBean.setLetures(listWithoutDuplicateElements);
					courseUserBean = new CourseUserBean(courseBean, userBean);
					courseUserBeanData.add(courseUserBean);
				} else {
					if (courseEntity.isActive()) {
						List<LectureEntity> lectures = courseEntity.getLectures();
						Set<LectureEntity> set = new HashSet<LectureEntity>(lectures);
						List<LectureEntity> listWithoutDuplicateElements = new ArrayList<LectureEntity>(set);

						UserEntity userEntity = userService.findUserByid(courseEntity.getUser().getId());
						Iterable<RoleEntity> iterable = userEntity.getRole();
						UserAboutCourseBean userBean = userConverter.convertUserAboutBean(userEntity);
						userBean.setRole(iterable.iterator().next().getRolename());
						CourseBean courseBean = courseConverter.convertBean(courseEntity);
						courseBean.setTotalStudentEnroll(courseEntity.getUsers().size());
						courseBean.setLetures(listWithoutDuplicateElements);
						courseUserBean = new CourseUserBean(courseBean, userBean);
						courseUserBeanData.add(courseUserBean);
					}
				}

			}

			responseBean.setData(courseUserBeanData);
			responseBean.setSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@GetMapping("/courses/{id}")
	private ResponseEntity<ResponseBean> getDetailCourse(@PathVariable Long id) {
		ResponseBean responseBean = new ResponseBean();
		MapBean mapBean = new MapBean();
		try {
			CourseEntity course = courseService.findById(id);
			UserEntity user = userService.findUserByid(course.getUser().getId());

			mapBean.put("course", course);
			mapBean.put("user", user);
			mapBean.put("totalStudentEnroll", course.getUsers().size());

			responseBean.setData(mapBean.getAll());
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setCouseIdNotFound();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
	}

	@PostMapping("/courses")
	private ResponseEntity<ResponseBean> addCourse(@RequestBody CourseBean courseBean, HttpServletRequest request) {
		System.out.println("aaa: " + courseBean);
		String authHeader = request.getHeader("Authorization");
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);
		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		CourseEntity courseEntity = courseConverter.convertEntity(courseBean);

		if (courseEntity != null) {
			CourseUserBean courseUserBean = new CourseUserBean();
			CourseBean courseBeanData = new CourseBean();

			UserEntity user = userService.findByToken(authHeader);
			courseEntity.setUser(user);
			UserEntity userEntityRoleTeacher = userService.findUserByid(courseEntity.getUser().getId());
			UserAboutCourseBean userBean = userConverter.convertUserAboutBean(userEntityRoleTeacher);

			Iterable<RoleEntity> role = userEntityRoleTeacher.getRole();

			CourseEntity course = courseService.save(courseEntity);

			Integer userJoinCourse = course.getUsers().size();

			courseBean = courseConverter.convertBean(course);
			courseBean.setTotalStudentEnroll(userJoinCourse);

			courseUserBean.setCourse(courseBean);
			courseUserBean.setTeacher(userBean);
			userBean.setRole(role.iterator().next().getRolename());

			responseBean.setData(courseUserBean);
			responseBean.setSuccess();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

//	@PostMapping("/courses/upload/{id}")
//	private ResponseEntity<ResponseBean> uploadCourse(@PathVariable long id, @RequestParam("file") MultipartFile file,
//			HttpServletRequest request) throws IOException {
//
//		return null;
//	}

	@PostMapping("/courses/upload/{id}")
	private ResponseEntity<ResponseBean> uploadCourse(@PathVariable long id, @RequestParam("file") MultipartFile file,HttpServletRequest request)
			throws IOException {

		System.out.println( getClass().getResource(getClass().getSimpleName() + ".class") );
        
		CourseEntity course = courseService.findById(id);
		ResponseBean responseBean = new ResponseBean();
		if (course != null) {
			boolean checkUpload = Util.upload(file);
			if (checkUpload) {
				String orginalFile = file.getOriginalFilename();
				String extension= orginalFile.substring(orginalFile.lastIndexOf(".") +1);
				course.setImage(orginalFile);
				
				CourseEntity courseEntity = courseService.save(course);
			
				responseBean.setData(courseEntity);
				responseBean.setSuccess();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			} else {
				responseBean.setFailUpload();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
			}
		}
		return null;
	}

//	@PostMapping("/courses/upload/v2/{id}")
//	private ResponseEntity<ResponseBean> uploadCourseV2(@PathVariable long id, @RequestParam("file") MultipartFile file,HttpServletRequest request)
//			throws IOException {
//		String pathSaveFile = context.getRealPath(Constant.UPLOAD_ROOT);
//		ResponseBean responseBean = new ResponseBean();
//		try {
//			CourseEntity course = courseService.findById(id);
//			if(course == null) {
//				responseBean.setCouseIdNotFound();
//				responseBean.setBadRequest();
//				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
//			}
//			boolean checkUpload = Util.uploadV2(file,pathSaveFile);
//			if (checkUpload) {
//				String orginalFile = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
//				String extension= file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") +1);
//				course.setImage(orginalFile);
//				MediaEntity media = new MediaEntity(Calendar.getInstance().getTimeInMillis(),orginalFile, extension);
//				mediaService.save(media);
//				CourseEntity courseEntity = courseService.save(course);
//			
//				responseBean.setData(courseEntity);
//				responseBean.setSuccess();
//				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
//			} else {
//				responseBean.setFailUpload();
//				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			responseBean.setBadRequest();
//			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
//		}
//	}

	@PutMapping("/courses/{id}")
	private ResponseEntity<ResponseBean> updateCourse(@PathVariable("id") Long id, @RequestBody CourseBean courseBean,
			HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);

		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		CourseEntity courseEntity = courseService.findById(id);
		if (courseEntity != null) {
			courseEntity.setName(courseBean.getName());
			courseEntity.setDescription(courseBean.getDescription());
			courseEntity.setActive(courseBean.isActive());
			CourseEntity course = courseService.save(courseEntity);

			UserEntity userEntityRoleTeacher = userService.findUserByid(courseEntity.getUser().getId());
			UserAboutCourseBean userBean = userConverter.convertUserAboutBean(userEntityRoleTeacher);

			Integer userJoinCourse = course.getUsers().size();

			Iterable<RoleEntity> role = userEntityRoleTeacher.getRole();
			userBean.setRole(role.iterator().next().getRolename());

			courseBean = courseConverter.convertBean(course);
			courseBean.setTotalStudentEnroll(userJoinCourse);

			CourseUserBean courseUserBean = new CourseUserBean(courseBean, userBean);

			responseBean.setData(courseUserBean);
			responseBean.setSuccess();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@DeleteMapping("/courses/{id}")
	private ResponseEntity<ResponseBean> deleteCourse(@PathVariable("id") Long id, HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();
		MapBean mapBean = new MapBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);

		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}

		CourseEntity course = courseService.findById(id);

		if (course.getUsers().size() > 0) {
			int status = HttpStatus.BAD_REQUEST.value();
			responseBean.setStatus(status);
			responseBean.setMessages("msg.userExist", "User is enrolement");
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}

		boolean result = courseService.delete(id);
		if (result) {
			responseBean.setData("{}");
			responseBean.setSuccess();
		} else {
			responseBean.setDeleteFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
}
