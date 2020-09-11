import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class DashboardService {
  listCourses() {
    const path = "/all-courses";
    return this._http.getNoAuth(path);
  }

  getCourse(id) {
    const path = "/courses/" + id;
    return this._http.getNoAuth(path);
  }
  listTeacher() {
    const path = "/users/teacher";
    return this._http.getNoAuth(path);
  }
  constructor(private _http: HttpService) { }
}
