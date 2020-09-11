import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class DetailCourseService {

  constructor(private _http:HttpService) { }
  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.getNoAuth(path);
  }

  getLectureByCourseId(idCourse){
    const path = "/lectures/courses/"+idCourse;
    return this._http.get(path);
  }

  enrollmentCourse(idUser,idCourse){
    const path = "/enrolements/users/"+idUser+"/courses/"+idCourse;
    return this._http.create(path);
  }

  update(courses: any, id: any) {
    const path = "/courses/" + id;
    return this._http.update(path, courses);
  }
}
