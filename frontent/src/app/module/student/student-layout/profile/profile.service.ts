import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class ProfileService {
  constructor(private _http: HttpService) { }

  getUserById(id) {
    const path = "/users/" + id;
    return this._http.get(path);
  }
  
  update(profile: any, id: any) {
    const path = "/users/update_profile/" + id;
    return this._http.update(path, profile);
  }

  changePassword(password){
    const path = "/users/change_password";
    return this._http.update(path, password);
  }
  uploadFile(formData,idUser){
    const path = "/users/upload/" + idUser;
    return this._http.postFile(formData,path);
  }
}
