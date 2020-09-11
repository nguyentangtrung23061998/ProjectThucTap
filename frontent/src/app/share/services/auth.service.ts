import { Injectable } from '@angular/core';
import { UserAccount } from '../model/user_account.module';
import { CookieService } from 'ngx-cookie-service';
import { role } from '../util/util.constant';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _userAccount: UserAccount;

  set userAccount(data: any) {
    if (this._userAccount instanceof UserAccount) {
      this._userAccount.deserialize(data);
    } else {
      this._userAccount = new UserAccount().deserialize(data);
    }
    const expiredDate = new Date();
    expiredDate.setDate(expiredDate.getDate() + 7);
   
    this.cookieService.set(
      'userAccount',
      JSON.stringify(this.userAccount),
      expiredDate,
      '/'
    );
  }

  get userAccount() {
    return this._userAccount instanceof UserAccount
      ? this._userAccount.serialize()
      : this._userAccount;
  }

  get token() {
    return this._userAccount instanceof UserAccount
      ? this._userAccount.getToken
      : null;
  }

  get isAdmin() {
    try {
      let test = JSON.parse(this.cookieService.get("userAccount"));;
      let userAccountCookie = JSON.parse(this.cookieService.get("userAccount"));
      this.userAccount = userAccountCookie;
      let roleAdmin = userAccountCookie['role'];
      if (roleAdmin['authority'] === role[0]) {
        return true;
      }
    } catch (error) {
      return false;
    }
  }

  get isTeacher() {
    try {
      let test = JSON.parse(this.cookieService.get("userAccount"));;
      let userAccountCookie = JSON.parse(this.cookieService.get("userAccount"));
      this.userAccount = userAccountCookie;
      let roleAdmin = userAccountCookie['role'];
      if (roleAdmin['authority'] === role[1]) {
        return true;
      }
    } catch (error) {
      return false;
    }
  }

  get isStudent(){
    try {
      let test = JSON.parse(this.cookieService.get("userAccount"));;
      let userAccountCookie = JSON.parse(this.cookieService.get("userAccount"));
      this.userAccount = userAccountCookie;
      let roleAdmin = userAccountCookie['role'];
      if (roleAdmin['authority'] === role[2]) {
        return true;
      }
      if (roleAdmin === role[2]) {
        return true;
      }
    } catch (error) {
      return false;
    }
    return false;
  }

  get isLoggedIn() {
    return this._userAccount instanceof UserAccount ? true : false;
  }

  logOut() {
    this._userAccount = null;
    this.cookieService.delete('userAccount', '/');
    this.deleteUnauthorizedCookie();
  }

  logIn(data) {
    this.userAccount = data;
  }

  constructor(private cookieService: CookieService) {

  }

  public setUnauthorizedCookie(message: string) {
    this.cookieService.set('UNAUTHORIZED', message, 1, '/');
  }

  public deleteUnauthorizedCookie() {
    this.cookieService.delete('UNAUTHORIZED', '/');
  }

  public getUnauthorizedCookie() {
    return this.cookieService.get('UNAUTHORIZED');
  }
}
