import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class ManagerTeacherService {

  constructor(private _http: HttpService) { }

  getCourseByTeacherId(id){
    const path = "/users/" + id+"/courses";
    return this._http.get(path);
  }

  getTeacherById(id) {
    const path = "/users/" + id;
    return this._http.get(path);
  }

  addTeacher(teacher: any) {
    const path = "/auth/signup";
    return this._http.create(path, teacher);
  }

  listTeacher() {
    const path = "/users/teacher";
    return this._http.get(path);
  }

  delete(id: any) {
    const path = "/users/" + id;
    return this._http.delete(path);
  }
  update(student: any, id: any) {
    const path = "/users/update_profile/" + id;
    return this._http.update(path, student);
  }
}
