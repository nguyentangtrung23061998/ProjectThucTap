import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as $ from 'jquery';
import { TeacherLoginService } from './teacher-login.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { Router } from '@angular/router';
import {role} from '../../../share/util/util.constant';

@Component({
  selector: 'app-teacher-login',
  templateUrl: './teacher-login.component.html',
  styleUrls: ['./teacher-login.component.scss'],
  providers: [TeacherLoginService]
})
export class TeacherLoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;

  showLoadingIndicator = false;

  public messageError: string = null;
  validate(input) {
    if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
      if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
        return false;
      }
    }
    else {
      if ($(input).val().trim() == '') {
        return false;
      }
    }
  }
  showValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).addClass('alert-validate');
  }

  hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
  }

  get f() { return this.loginForm.controls; }

  constructor(private formBuilder: FormBuilder,
    private teacherLoginService: TeacherLoginService,
    private router : Router) { }

  onSubmit() {
    this.submitted = true;
    const diabledButton = <HTMLInputElement>document.getElementById('btnLogin');

    if (this.loginForm.invalid) {
      return;
    }

    if (diabledButton != null) {
      diabledButton.disabled = true;
    }

    this.showLoadingIndicator = true;
    this.teacherLoginService.login(this.loginForm.value).subscribe(
      (kq) => {
        console.log(kq)
        if ((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority === role[1])) {
          this.teacherLoginService.setLogin(kq.data);
          this.router.navigate(['/teacher']);
        } 
        else if((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority !== role[1])){
          this.showLoadingIndicator = false;
          alert("You don't have role in system");
        } else{
          this.showLoadingIndicator = false;
          if (diabledButton != null) {
            diabledButton.disabled = false;
          }
          alert("Passoword is invalid. You try click button 'forget email' is reset password");
          this.messageError = kq.message;
        }
      }, (error: any) => {
        console.log('error: ', error);
      }
    )
  }

  ngOnInit() {
    $('.input100').each(function () {
      $(this).on('blur', function () {
        if ($(this).val().trim() != "") {
          $(this).addClass('has-val');
        }
        else {
          $(this).removeClass('has-val');
        }
      })
    })
    var input = $('.validate-input .input100');
    var showPass = 0;
    $('.btn-show-pass').on('click', function () {
      if (showPass == 0) {
        $(this).next('input').attr('type', 'text');
        $(this).find('i').removeClass('zmdi-eye');
        $(this).find('i').addClass('zmdi-eye-off');
        showPass = 1;
      }
      else {
        $(this).next('input').attr('type', 'password');
        $(this).find('i').addClass('zmdi-eye');
        $(this).find('i').removeClass('zmdi-eye-off');
        showPass = 0;
      }

    });

    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
  }

}
