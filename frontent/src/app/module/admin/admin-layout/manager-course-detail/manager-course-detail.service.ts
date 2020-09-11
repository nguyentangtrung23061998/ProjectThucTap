import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class ManagerCourseDetailService {

  constructor(private _http: HttpService) { }

  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.get(path);
  }

  getLectureByCourseId(idCourse){
    const path = "/lectures/courses/"+idCourse;
    return this._http.get(path);
  }

  update(courses: any, id: any) {
    const path = "/courses/" + id;
    return this._http.update(path, courses);
  }
}
