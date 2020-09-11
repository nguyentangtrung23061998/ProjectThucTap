import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/share/services/auth.service';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class StudentAuthenticationService {
  constructor(private _http: HttpService,
    private authService: AuthService) { }

  login(adminStudent: any) {
    const path = '/auth/signin';
    return this._http.createNoAuth(path, adminStudent)
  }

  setLogin(data) {
    this.authService.logIn(data);
    this._http.setAuthToken(data.token);
  }

  setToken(data) {
    this._http.setAuthToken(data.token);
  }
  addStudent(student: any) {
    const path = "/auth/signup";
    return this._http.create(path, student);
  }
}
