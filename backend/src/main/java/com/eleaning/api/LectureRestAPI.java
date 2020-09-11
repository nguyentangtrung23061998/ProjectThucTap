package com.eleaning.api;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;
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

import com.eleaning.bean.LectureBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.RoleNameBean;
import com.eleaning.conveter.LectureConverter;
import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.LectureEntity;
import com.eleaning.service.ICourseService;
import com.eleaning.service.ILectureService;
import com.eleaning.service.IUserService;
import com.eleaning.util.Constant;
import com.eleaning.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lectures")
public class LectureRestAPI {

	private static final Logger logger = LoggerFactory.getLogger(LectureRestAPI.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private ILectureService lectureService;
	
	@Autowired 
	private ICourseService courseService;

	@Autowired
	private LectureConverter lectureConverter;

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

	@GetMapping("")
	private ResponseEntity<ResponseBean> getLecture() {
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			List list = (List) authentication.getAuthorities();
			boolean check = checkRole(list);
//			if (!check) {
//				responseBean.setRoleFail();
//				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
//			}

			List<LectureEntity> lectures = lectureService.getAll();
			if (lectures != null) {
				responseBean.setData(lectures);
				responseBean.setSuccess();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<ResponseBean> getLectureById(@PathVariable Long id) {
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			List list = (List) authentication.getAuthorities();
//			boolean check = checkRole(list);
//			if (!check) {
//				responseBean.setRoleFail();
//				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
//			}

			LectureEntity lectureEntity = lectureService.findById(id);
			if (lectureEntity != null) {
				responseBean.setData(lectureEntity);
				responseBean.setSuccess();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@GetMapping("/courses/{courseId}")
	private ResponseEntity<ResponseBean> getAllLectureByCourse(@PathVariable Long courseId) {
		ResponseBean responseBean = new ResponseBean();
		try {
			List<LectureEntity> lecture = lectureService.getLectureByCourse(courseId);
			if(lecture != null) {
				responseBean.setData(lecture);
				responseBean.setSuccess();
			}else {
				responseBean.setNotFound();
				return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
	}
	
	@PostMapping("/courses/{courseId}")
	private ResponseEntity<ResponseBean> addLecture(@PathVariable Long courseId,@RequestBody LectureBean lectureBean, HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);
		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
		
		if (lectureBean.getName() == null ) {
			responseBean.setEnterAllRequiredFields();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
		
		CourseEntity courseEntity = courseService.findById(courseId);

		LectureEntity lectureEntity = lectureConverter.convertEntity(lectureBean);
		if (lectureEntity != null) {
			lectureEntity.setCourse(courseEntity);
			LectureEntity lecture = lectureService.save(lectureEntity);
			responseBean.setData(lecture);
			responseBean.setSuccess();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	private ResponseEntity<ResponseBean> updateLecture(@PathVariable("id") Long id,
			@RequestBody LectureBean lectureBean, HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);

		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		if (lectureBean.getName() == null) {
			responseBean.setEnterAllRequiredFields();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}

		LectureEntity lectureEntity = lectureService.findById(id);
		System.out.println(lectureEntity);
		if (lectureEntity.getId() != null) {
			lectureEntity.setName(lectureBean.getName());
			lectureEntity.setDescription(lectureBean.getDescription());
			LectureEntity lecture = lectureService.save(lectureEntity);
			responseBean.setData(lecture);
			responseBean.setSuccess();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}else {
			responseBean.setBadRequest();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
		
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<ResponseBean> deleteCourse(@PathVariable("id") Long id, HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);

		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}
		boolean result = lectureService.delete(id);
		if (result) {
			responseBean.setData("{}");
			responseBean.setSuccess();
		} else {
			responseBean.setDeleteFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PostMapping("/upload/{id}")
	private ResponseEntity<ResponseBean> uploadUser(@PathVariable long id, @RequestParam("file") MultipartFile file,HttpServletRequest request)
			throws IOException {
		LectureEntity lecture = lectureService.findById(id);
		ResponseBean responseBean = new ResponseBean();

		if (lecture != null) {
			boolean checkUpload = Util.upload(file);
			if (checkUpload) {
				String orginalFile = file.getOriginalFilename();
				String extension= orginalFile.substring(orginalFile.lastIndexOf(".") +1);
				for (String extensionVideo : Constant.extensionVideo) {
					if (extension.equals(extensionVideo)) {
						lecture.setVideo(orginalFile);
					}
				}
				for (String extensionImg : Constant.extensionImg) {
					if (extension.equals(extensionImg)) {
						lecture.setImage(orginalFile);
					}
				}
				for (String extensionAudio : Constant.extensionAudio) {
					if (extension.equals(extensionAudio)) {
						lecture.setAudio(orginalFile);
					}
				}
				for (String extensionDocument : Constant.extensionDocument) {
					if (extension.equals(extensionDocument)) {
						lecture.setDocument(orginalFile);
					}
				}
				LectureEntity lectureSave = lectureService.save(lecture);
				responseBean.setData(lectureSave);
				responseBean.setSuccess();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			} else {
				responseBean.setFailUpload();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
			}
		}
		return null;
	}
}
