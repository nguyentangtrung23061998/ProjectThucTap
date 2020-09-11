import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class TeacherChangePasswordService {
  changePassword(password){
    const path = "/users/change_password";
    return this._http.update(path, password);
  }
  constructor(private _http : HttpService) { }
}
