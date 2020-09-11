import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/share/services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { FormGroup, FormBuilder } from '@angular/forms';
import { role } from '../../../../share/util/util.constant';
import * as $ from 'jquery';
import { api_url } from '../../../../config/api_url.config';
import { TeacherChangePasswordService } from './teacher-change-password.service';
import { RouterEvent, Router } from '@angular/router';
import { ConfirmedValidator } from './confirmed.validator';
@Component({
  selector: 'app-teacher-change-password',
  templateUrl: './teacher-change-password.component.html',
  styleUrls: ['./teacher-change-password.component.scss'],
  providers: [TeacherChangePasswordService]
})
export class TeacherChangePasswordComponent implements OnInit {
  formProfile: FormGroup;
  formChangePassword: FormGroup;
  public userObj = this.authService.userAccount;
  public userObjNew = {
    username: null,
    email: null,
    fullname: null,
    image: null
  };
  public edit = true;
  public urlUpload = api_url.upload;

  constructor(private changePasswordService: TeacherChangePasswordService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router) { }


  onChangePassword() {
    this.changePasswordService.changePassword(this.formChangePassword.value).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          alert('Change password is success');
          this.formChangePassword.reset();
          $('#modelPassword').trigger('click');
        }
        if (kq.status === ServerResponseCode.ERROR) {
          alert(kq.message);
        }
      }
    )
  }

  get f(){
    return this.formChangePassword.controls;
  }

  ngOnInit() {
    this.formChangePassword = this.formBuilder.group({
      currentPassword: [],
      newPassword: [],
      confirmPassword:[]
    },{
      validator: ConfirmedValidator('newPassword', 'confirmPassword')
    })
  }

}
