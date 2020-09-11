import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/share/services/http.service';
import { AuthService } from 'src/app/share/services/auth.service';

@Injectable()
export class TeacherLoginService {
  constructor(private _http: HttpService,
    private authService: AuthService) { }

  login(adminLogin){
    const path = '/auth/signin';
		return this._http.createNoAuth(path,adminLogin)
  }
  
	setLogin(data){
		this.authService.logIn(data);
		this._http.setAuthToken(data.token);
  }
  
  setToken(data){
		this._http.setAuthToken(data.token);
  }
}
