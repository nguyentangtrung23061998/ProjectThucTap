import { Component, OnInit, AfterViewInit } from '@angular/core';
import * as $ from "jquery";
import { AuthService } from 'src/app/share/services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { api_url } from '../../../config/api_url.config';
import { Router } from '@angular/router';
import { role } from '../../../share/util/util.constant';
import { StudentLayoutService } from './student-layout.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
@Component({
  selector: 'app-student-layout',
  templateUrl: './student-layout.component.html',
  styleUrls: ['./student-layout.component.scss'],
  providers:[StudentLayoutService]
})
export class StudentLayoutComponent implements OnInit, AfterViewInit {
  public authUser = null;
  public isLogin = false;
  public urlUpload = api_url.upload;
  public userObj ={
    role:role[2],
    token:''
  };

  authentication() {
    this.router.navigate(['/authentication']);
  }


  constructor(private cookieService: CookieService,
    private router: Router,
    private authService: AuthService,
    private studentLayoutService:StudentLayoutService) {
  }

  getUrlParameter(name) {
    let hrefUrl = this.router.url;
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

    var results = regex.exec(hrefUrl);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

  getUserSocial(token){
    this.studentLayoutService.getSocial(token).subscribe(
      (kq:any)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.userObj= kq.data;
          this.userObj.token= token;
          this.userObj.role = role[2];
          this.studentLayoutService.setLogin(this.userObj);
          this.authUser = this.userObj;
          this.isLogin=true;
          console.log(this.authUser)
        }
      },(error)=>{
        console.log(error)
      }
    )
  }

  signOut() {
    this.authService.logOut();
    this.isLogin = false;
    this.router.navigate([""]);
  }

  ngOnInit() {
    let token ='Bearer '+ this.getUrlParameter('token');
    // this.getUserSocial(token)
    if(token){
      this.getUserSocial(token)
    }

    this.authUser = this.authService;
    if (this.authUser.isStudent) {
      if (this.authUser != null) {

        this.authUser = this.authUser.userAccount;
        this.authService.isStudent;
        this.isLogin = true;
      }
    }
    // if((objUser.length>0) ){
    //   this.authUser = JSON.parse(objUser);
    //   if(this.authUser != null){
    //     this.isLogin=true;
    //   }
    // }   
  }


  ngAfterViewInit() {

  }

}
