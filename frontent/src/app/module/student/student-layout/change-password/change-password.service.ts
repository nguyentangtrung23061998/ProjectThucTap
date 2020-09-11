import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class ChangePasswordService {

  constructor(private _http:HttpService) { }
  changePassword(password){
    const path = "/users/change_password";
    return this._http.update(path, password);
  }
}
