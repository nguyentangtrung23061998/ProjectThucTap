import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class TeacherAllCourseService {

  constructor(private _http: HttpService) { }

  listCourses() {
    const path = "/all-courses";
    return this._http.get(path);
  }

  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.get(path);
  }
}
