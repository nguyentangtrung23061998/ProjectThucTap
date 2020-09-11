import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class TeacherAllCourseDetailService {

  constructor(private _http: HttpService) { }

  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.get(path);
  }

  getLectureById(id) {
    const path = "/lectures/" + id;
    return this._http.get(path);
  }
  
  getAllLecture(){
    const path = "/lectures";
    return this._http.get(path);
  }

  getLectureByCourseId(idCourse){
    const path = "/lectures/courses/"+idCourse;
    return this._http.get(path);
  }
  listQuestionByLectureId(id) {
    const path = "/questions/lectures/"+id;
    return this._http.get(path);
  }
  addLectureByCourseId(lecture,idCourse){
    const path = "/lectures/courses/"+idCourse;
    return this._http.create(path,lecture);
  }

  updateLecture(lecture,idLecture){
    const path = "/lectures/"+idLecture;
    return this._http.update(path,lecture);
  }

  deleteLecture(idLecture){
    const path="/lectures/"+idLecture;
    return this._http.delete(path);
  }

  uploadFile(formData,idLecture){
    const path = "/lectures/upload/" + idLecture;
    return this._http.postFile(formData,path);
  }
}
