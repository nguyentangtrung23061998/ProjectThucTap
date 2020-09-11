package com.eleaning.api;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.eleaning.bean.QuestionsBankBean;
import com.eleaning.bean.ResponseBean;
import com.eleaning.bean.RoleNameBean;
import com.eleaning.conveter.QuestionsBankConverter;
import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.LectureEntity;
import com.eleaning.entity.QuestionsBankEntity;
import com.eleaning.service.ICourseService;
import com.eleaning.service.ILectureService;
import com.eleaning.service.IQuestionsBankService;
import com.eleaning.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionsBankRestAPI {

	private static final Logger logger = LoggerFactory.getLogger(QuestionsBankRestAPI.class);

	@Autowired
	private IQuestionsBankService questionsBankService;

	@Autowired
	private QuestionsBankConverter questionsBankConverter;

	@Autowired
	ICourseService courseService;
	
	@Autowired
	ILectureService lectureService;

	@Autowired
	private IUserService userService;

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
	private ResponseEntity<ResponseBean> getQuestionBankAll() {
		ResponseBean responseBean = new ResponseBean();
		try {
			List<QuestionsBankEntity> questionsBanks = questionsBankService.getAll();
			if (questionsBanks != null) {
				responseBean.setData(questionsBanks);
				responseBean.setSuccess();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<ResponseBean> getQuestionById(@PathVariable Long id) {
		ResponseBean responseBean = new ResponseBean();
		try {
//			List<QuestionsBankEntity> questionsBanks = questionsBankService.getAll();
			QuestionsBankEntity questionsBankEntity = questionsBankService.findById(id);
			if (questionsBankEntity != null) {
				responseBean.setData(questionsBankEntity);
				responseBean.setSuccess();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@GetMapping("/lectures/{lectureid}")
	private ResponseEntity<ResponseBean> getQuestionsBanksId(@PathVariable Long lectureid,HttpServletRequest request) {
		ResponseBean responseBean = new ResponseBean();
		try {
			List<QuestionsBankEntity> questionsBanks = questionsBankService.getQuestionBankByLecture(lectureid);
			if (questionsBanks != null) {
				responseBean.setData(questionsBanks);
				responseBean.setSuccess();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PostMapping("/lectures/{lectureid}")
	private ResponseEntity<ResponseBean> addExamCourse(@PathVariable Long lectureid,
			@RequestBody QuestionsBankBean questionsBank) {
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);
		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
		try {
			QuestionsBankEntity questionsBankEntity = questionsBankConverter.convertEntity(questionsBank);
			if (questionsBank.getQuestion() == null || questionsBank.getAnswerfirst() == null
					|| questionsBank.getAnswersecond() == null || questionsBank.getAnswerthird() == null
					|| questionsBank.getAnswerfourth() == null || questionsBank.getCorrectanswer() == null) {
				responseBean.setEnterAllRequiredFields();
				return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			}
			if (questionsBankEntity != null) {
				
				LectureEntity lecture = lectureService.findById(lectureid);
				if(lecture == null ) {
					responseBean.setIdObjectNotFound();
					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				}
				questionsBankEntity.setLecture(lecture);
				QuestionsBankEntity questionsBankEntityData= questionsBankService.save(questionsBankEntity);
				QuestionsBankBean questionsBankBeanData = questionsBankConverter.convertBean(questionsBankEntityData);
				questionsBankBeanData.setLectureId(questionsBankBeanData.getLectureId());
				responseBean.setData(questionsBankBeanData);
				responseBean.setSuccess();
			}
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@PutMapping("/{questionBankId}")
	private ResponseEntity<ResponseBean> updateQuestionBank(@PathVariable("questionBankId") Long questionsBankId,
			 @RequestBody QuestionsBankBean questionsBankBean) {
		ResponseBean responseBean = new ResponseBean();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);

		if (!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}

		if (questionsBankBean.getQuestion() == null || questionsBankBean.getAnswerfirst() == null
				|| questionsBankBean.getAnswersecond() == null || questionsBankBean.getAnswerthird() == null
				|| questionsBankBean.getAnswerfourth() == null || questionsBankBean.getCorrectanswer() == null) {
			responseBean.setEnterAllRequiredFields();
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		}
		try {
			QuestionsBankEntity questionsBankEntity = questionsBankService.findById(questionsBankId);
			if (questionsBankEntity != null) {
				questionsBankEntity.setQuestion(questionsBankBean.getQuestion());;
				questionsBankEntity.setAnswerfirst(questionsBankBean.getAnswerfirst());
				questionsBankEntity.setAnswersecond(questionsBankBean.getAnswersecond());
				questionsBankEntity.setAnswerthird(questionsBankBean.getAnswerthird());
				questionsBankEntity.setAnswerfourth(questionsBankBean.getAnswerfourth());
				questionsBankEntity.setCorrectanswer(questionsBankBean.getCorrectanswer());
				
				QuestionsBankEntity questionsBankEntityData = questionsBankService.save(questionsBankEntity);
				QuestionsBankBean questionBankData = questionsBankConverter.convertBean(questionsBankEntityData);
				responseBean.setData(questionBankData);
				responseBean.setSuccess();
			}
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<ResponseBean> deleteQuestionsBankById(@PathVariable("id") Long id){
		ResponseBean responseBean = new ResponseBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List list = (List) authentication.getAuthorities();
		boolean check = checkRole(list);
		
		if(!check) {
			responseBean.setRoleFail();
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
		}
		try {
			boolean result = questionsBankService.delete(id);
			if(result) {
				responseBean.setData("{}");
				responseBean.setSuccess();
			}else {
				responseBean.setDeleteFail();
				return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
			}
			
			return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
	}
}
