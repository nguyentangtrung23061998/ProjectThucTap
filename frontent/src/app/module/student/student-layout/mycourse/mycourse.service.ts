import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class MycourseService {

  constructor(private _http:HttpService) { }
  
  enrollmentCourse(idUser){
    const path = "/enrolements/users/"+idUser;
    return this._http.get(path);
  }

  getCourseByUserId(id){
    const path = "/users/" + id+"/courses";
    return this._http.get(path);
  }

  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.get(path);
  }

  addCourse(course: any) {
    console.log(course)
    const path = "/courses";
    return this._http.create(path, course);
  }

  listCourses() {
    const path = "/all-courses";
    return this._http.get(path);
  }

  delete(id: any) {
    const path = "/courses/" + id;
    return this._http.delete(path);
  }
  update(courses: any, id: any) {
    const path = "/courses/" + id;
    return this._http.update(path, courses);
  }

  uploadAvatar(formData,id){
    const path = "/courses/upload/" + id;
    return this._http.postFile(formData,path);
  }
}
