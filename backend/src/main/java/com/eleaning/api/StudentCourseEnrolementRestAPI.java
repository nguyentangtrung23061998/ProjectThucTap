package com.eleaning.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eleaning.bean.CourseBean;
import com.eleaning.bean.CourseUserBean;
import com.eleaning.bean.MapBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.RoleNameBean;
import com.eleaning.bean.UserAboutCourseBean;
import com.eleaning.conveter.CourseConverter;
import com.eleaning.conveter.UserConverter;
import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.RoleEntity;
import com.eleaning.entity.UserEntity;
import com.eleaning.service.ICourseService;
import com.eleaning.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class StudentCourseEnrolementRestAPI {

	private static final Logger logger = LoggerFactory.getLogger(StudentCourseEnrolementRestAPI.class);
	private static final String[] roleNoAccess = {"ROLE_ADMIN","ROLE_TEACHER"};
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private CourseConverter courseConverter;
	
	@Autowired 
	private UserConverter userConverter;
	
	public boolean checkRole(List list) {
		for (int i = 0; i < list.size(); i++) {
			String role = String.valueOf(list.get(i));
			RoleNameBean strRole = RoleNameBean.ROLE_STUDENT;
			String roleStudent = strRole.getValue();
			if(role.equals(roleStudent))
				return true;
		}
		return false;
	}
		
	@GetMapping("/enrolements/users/{userid}")
	public ResponseEntity<ResponseBean> getEnrolement(@PathVariable Long userid){
		ResponseBean responseBean = new ResponseBean();
		List<CourseUserBean> courseUserBeans = new ArrayList<CourseUserBean>();
		MapBean map = new MapBean();
		try {
			UserEntity userEntity = userService.findUserByid(userid);
			System.out.println("User Entity: " + userEntity.getCourser_enroll().size());
			Set<CourseEntity> courses = userEntity.getCourser_enroll();
			
			Iterator<CourseEntity> courseData = courses.iterator();
			while(courseData.hasNext()) {
				CourseUserBean courseUserBean = new CourseUserBean();
				CourseEntity courseEntity = courseData.next();
				
				UserEntity userEntityRoleTeacher = userService.findUserByid(courseEntity.getUser().getId());
				
				CourseBean courseBean = courseConverter.convertBean(courseEntity);
				UserAboutCourseBean user = userConverter.convertUserAboutBean(courseEntity.getUser());
				
				Iterable<RoleEntity> role = userEntityRoleTeacher.getRole();
				
				courseBean.setTotalStudentEnroll(courseEntity.getUsers().size());
				user.setRole(role.iterator().next().getRolename());
				
				courseUserBean.setCourse(courseBean);
				courseUserBean.setTeacher(user);
				courseUserBeans.add(courseUserBean);
				
			}
			
			responseBean.setData(courseUserBeans);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
		}
	}
	
	@PostMapping("/enrolements/users/{userid}/courses/{courseid}")
	public ResponseEntity<ResponseBean> enrolementStudent(@PathVariable Long userid, @PathVariable Long courseid){
		ResponseBean responseBean = new ResponseBean();
		MapBean mapBean = new MapBean();
		try {			
			Set<CourseEntity> courses = new HashSet<CourseEntity>();
			Set<UserEntity> users = new HashSet<UserEntity>();
			
			boolean flag=false;
			UserEntity user = userService.findUserByid(userid);
			Iterable<RoleEntity> role = user.getRole();
			for(String roleNoAccess: roleNoAccess) {
				if(roleNoAccess.equals(String.valueOf(role.iterator().next().getRolename()))) {
					flag=true;
					break;
				}
			}
			if(flag) {
				responseBean.setRoleFail();
				return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
			}else {
				// course nay add vao enroll
				boolean checkCourseNotExist = true;
				CourseEntity course = courseService.findById(courseid);
				
				courses = user.getCourser_enroll();
				Iterator<CourseEntity> iterable = courses.iterator();
				System.out.println("courses size: " + courses.size());
				
				
				while(iterable.hasNext()) {
					CourseEntity courseData  = iterable.next();
					System.out.println("Course id: " + courseData.getId());
					if(courseData.getId().equals(courseid)) {
						checkCourseNotExist =  false;
						courses.add(courseData);
					}
				}
				
				if(checkCourseNotExist == false) {
					responseBean.setMessages("msg.joinExist", "User is joining course");
					responseBean.setStatus(HttpStatus.OK.value());
					return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
				}
				
				courses.add(course);
				
				CourseBean courseBean = courseConverter.convertBean(course);
				courseBean.setTotalStudentEnroll(course.getUsers().size());
				user.setCourser_enroll(courses);
				
				System.out.println("user courser enroll: " + user.getCourser_enroll().size());
				
				userService.save(user);
				
				mapBean.put("username", user.getUsername());
				mapBean.put("email", user.getEmail());
				mapBean.put("fullname", user.getFullname());
				mapBean.put("course",courseBean);
				
//				
//				CourseEntity course = courseService.findById(courseid);
//				Set<UserEntity> users = course.getUsers();
//					
//				
//				
//				CourseBean courseBean = courseConverter.convertBean(course);
//				courseBean.setTotalStudentEnroll(course.getUsers().size());
//				courses.add(course);
//				user.setCourser_enroll(courses);
//				
//				course.setUsers(users);
//				
//				userService.save(user);
//				
//				mapBean.put("username", user.getUsername());
//				mapBean.put("email", user.getEmail());
//				mapBean.put("fullname", user.getFullname());
//				mapBean.put("course",courseBean);
				
				responseBean.setData(mapBean);
				responseBean.setSuccess();
				return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
		}
	}
}
