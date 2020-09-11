import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/share/services/auth.service';
import { HttpService } from 'src/app/share/services/http.service';

@Injectable()
export class StudentLayoutService {
    constructor(private _http: HttpService,
        private authService: AuthService) { }

    getSocial(token) {
        const path = '/users/me';
        return this._http.getSocial(path,token)
    }

    setLogin(data) {
        this.authService.logIn(data);
        this._http.setAuthToken(data.token);
    }
}
