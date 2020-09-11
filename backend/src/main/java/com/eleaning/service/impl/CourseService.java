package com.eleaning.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.tools.OptionChecker;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleaning.entity.CourseEntity;
import com.eleaning.entity.QuestionsBankEntity;
import com.eleaning.entity.LectureEntity;
import com.eleaning.repository.ICourseRepository;
import com.eleaning.repository.IQuestionsBankRepository;
import com.eleaning.repository.ILectureRepository;
import com.eleaning.service.ICourseService;
import com.eleaning.service.IQuestionsBankService;
import com.eleaning.service.ILectureService;
import com.eleaning.service.IUserService;

@Service
public class CourseService implements ICourseService{

	@Autowired
	private ICourseRepository courseRepository;
	
	@Autowired
	private ILectureService lectureService;
	
	@Autowired
	private IQuestionsBankService examCourseService;
	
	@Autowired 
	private IUserService userService;
	
	@Override
	public CourseEntity save(CourseEntity course) {
		try {
			if(course.getId() == null) {
				course.setId(Calendar.getInstance().getTimeInMillis());
				course.setCreateddate(new Timestamp(System.currentTimeMillis()));
			}
			else{
				course.setModifieddate(new Timestamp(System.currentTimeMillis()));
			}
			courseRepository.save(course);
			return course;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CourseEntity> getAll() {
		try {
			List<CourseEntity> result =new ArrayList<CourseEntity>();
			courseRepository.findAll().forEach(result::add);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<CourseEntity>();
	}

	@Override
	public boolean delete(Long id) {
		try {
			System.out.println("id: " + id);
			List<LectureEntity> getLectures = lectureService.getLectureByCourse(id);
			List<QuestionsBankEntity> exCourses = examCourseService.getQuestionBankByLecture(id);
		
			for (LectureEntity lecture : getLectures) {
				lectureService.delete(lecture.getId());
//				lectureService.delete()
			}
			
			for(QuestionsBankEntity exCourse : exCourses) {
				examCourseService.delete(exCourse.getId());
			}
			courseRepository.deleteCourseById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CourseEntity findById(Long id) {
		try {
			Optional<CourseEntity> data = courseRepository.findById(id);
			if(data.isPresent()) {
				return data.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public List<CourseEntity> getCoursesByUserId(Long userId) {
		try {
			List<CourseEntity> results = courseRepository.getAlByCourseId(userId);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
