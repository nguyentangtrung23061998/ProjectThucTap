import { Injectable, Injector } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { api_url } from '../../config/api_url.config';
import { catchError, tap, retry, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment.prod';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { ServerResponseCode } from './../util/server-response-code.constant';
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import * as util from '../util/util.constant';

export interface Response {
  status: number;
  data: any;
  messageKey?: string;
  message?: string;
  token?: string;
}

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  private options: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    observe: 'response'
  });

  private apiUrl = '';

  private getUserCookie() {
    let token = JSON.parse(this.cookieService.get("userAccount"))
    return token['token'];
  }

  private optionsWithAuth: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    observe: 'response'
  });

  constructor(private http: HttpClient,
    private authService: AuthService,
    private injector: Injector,
    private cookieService: CookieService) {
    this.apiUrl = api_url.domain;
  }

  public getNoAuth(path: string, param?: any) {
    if (param) {
      let params = new HttpParams();
      Object.keys(param).forEach(function (key, index) {
        params = params.append(key, param[key]);
      });
      return this.http
        .get<Response>(this.apiUrl + path, {
          headers: this.options,
          params: params
        })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    } else {
      return this.http
        .get<Response>(this.apiUrl + path, { headers: this.options })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    }
  }

  public getSocial(path:string,token:string,param?:any){
    var tokenNew = 'Bearer ' +token;
    this.optionsWithAuth = this.optionsWithAuth.set('Authorization', tokenNew);
    if (param) {
      let params = new HttpParams();
      Object.keys(param).forEach(function (key, index) {
        params = params.append(key, param[key]);
      });
      return this.http
        .get<Response>(this.apiUrl + path, {
          headers: this.optionsWithAuth,
          params: params
        })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    } else {
      return this.http
        .get<Response>(this.apiUrl + path, { headers: this.optionsWithAuth })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    }
  }

  public get(path: string, param?: any) {
    // if (this.authService !== null) {
    //   this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.token);
    // }
    if(this.authService.userAccount.token !== null){
      this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.token);
    }else{
      this.options;
    }
    console.log(this.optionsWithAuth)
    if (param) {
      let params = new HttpParams();
      Object.keys(param).forEach(function (key, index) {
        params = params.append(key, param[key]);
      });
      return this.http
        .get<Response>(this.apiUrl + path, {
          headers: this.optionsWithAuth,
          params: params
        })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    } else {
      return this.http
        .get<Response>(this.apiUrl + path, { headers: this.optionsWithAuth })
        .pipe(
          retry(3),
          tap((res: Response) => res),
          catchError(this.handleError.bind(this))
        );
    }
  }

  public createNoAuth(path: string, dataPost?: any) {
    return this.http
      .post<Response>(this.apiUrl + path, dataPost ? dataPost : {}, {
        headers: this.options
      })
      .pipe(
        retry(3),
        tap((res: Response) => res),
        catchError(this.handleError.bind(this))
      );
  }

  public create(path: string, dataPost?: any) {
    if (this.authService !== null) {
      this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.token);
    }
    return this.http
      .post<Response>(this.apiUrl + path, dataPost ? dataPost : {}, {
        headers: this.optionsWithAuth
      })
      .pipe(
        retry(3),
        tap((res: Response) => res),
        catchError(this.handleError.bind(this))
      );
  }

  public delete(path) {
    if (this.authService !== null) {
      this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.token);
    }
    return this.http
      .delete<Response>(this.apiUrl + path, {
        headers: this.optionsWithAuth
      })
      .pipe(
        retry(3),
        tap((res: Response) => res),
        catchError(this.handleError.bind(this))
      );
  }

  public update(path: string, dataPost?: any) {
    if (this.authService !== null) {
      this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.token);
    }
    return this.http
      .put<Response>(this.apiUrl + path, dataPost ? dataPost : {}, {
        headers: this.optionsWithAuth
      })
      .pipe(
        retry(3),
        tap((res: Response) => res),
        catchError(this.handleError.bind(this))
      );
  }

  postFile(fileToUpload,path){
    if (this.authService !== null) {
      this.optionsWithAuth = this.optionsWithAuth.set('Authorization', this.authService.userAccount.token);
    }
    var formData = new FormData();
    formData.append('file', fileToUpload);
    var httpHeaders: HttpHeaders = new HttpHeaders({
      Authorization: this.authService.token
    });

    return this.http.post<Response>(this.apiUrl + path, formData, {
      headers: httpHeaders
    })
      .pipe(
        retry(3),
        tap((res: Response) => Response)
      );
  }
  public setAuthToken(token) {
    this.optionsWithAuth = this.optionsWithAuth.set('Authorization', token);
  }

  public removeAuthToken() {
    this.optionsWithAuth = this.optionsWithAuth.delete('Authorization');
  }

  private handleError(error: HttpErrorResponse) {
    console.log("error: ", error)
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // Invalid token case :
    // if (error.status === ServerResponseCode.UNAUTHORIZED) {

    //   this.authService.logOut();
    //   this.authService.setUnauthorizedCookie(error.error.message);

    //   const router = this.injector.get(Router);
    //   if (router.url != null && router.url.includes('/admin/')) {
    //     router.navigate(['/admin/login']);
    //   } else {
    //     router.navigate(['/login']);
    //   }
    // }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
