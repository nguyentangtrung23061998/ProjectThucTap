import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class AllCouresService {

  constructor(private _http:HttpService) { }
  listCourses() {
    const path = "/all-courses";
    return this._http.get(path);
  }
  enrollmentCourse(idUser,idCourse){
    const path = "/enrolements/users/"+idUser+"/courses/"+idCourse;
    return this._http.create(path);
  }
}
