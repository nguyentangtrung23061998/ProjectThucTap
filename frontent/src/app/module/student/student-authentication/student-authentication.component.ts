import { Component, OnInit, AfterViewInit, OnChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { StudentAuthenticationService } from './student-authentication.service';
import { AuthService } from 'src/app/share/services/auth.service';
import { Router } from '@angular/router';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { role } from '../../../share/util/util.constant'
import * as $ from 'jquery';
import * as util from '../../../share/util/util.constant';
@Component({
  selector: 'app-student-authentication',
  templateUrl: './student-authentication.component.html',
  styleUrls: ['./student-authentication.component.scss'],
  providers: [StudentAuthenticationService]
})
export class StudentAuthenticationComponent implements OnInit, AfterViewInit, OnChanges {
  loginForm: FormGroup;
  signUpForm: FormGroup;
  submitted = false;

  showLoadingIndicator = false;

  public messageError: string = null;
  public isLogin = true;

  private urlGmail = util.GOOGLE_AUTH_URL;
  private urlFacebook = util.FACEBOOK_AUTH_URL;

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

  // get f() { return this.loginForm.controls; }

  


  constructor(private formBuilder: FormBuilder,
    private teacherLoginService: StudentAuthenticationService,
    private router: Router,
    private authService: AuthService) {
    $('.input100').each(function () {
      $('.input100').on('blur', function () {
        if ($(this).val().trim() != "") {
          $(this).addClass('has-val');
        }
        else {
          $(this).removeClass('has-val');
        }
      })
    })
  }

  signUp() {
    this.isLogin = false;
  }

  login() {
    this.isLogin = true;
  }

  onSignUp() {
    this.submitted = true;
    if (this.signUpForm.value.email === null || this.signUpForm.value.email === '') {
      return;
    } else if (this.signUpForm.value.username === null || this.signUpForm.value.username === '') {
      return;
    } else if (this.signUpForm.value.password === null || this.signUpForm.value.password === '') {
      return;
    } else if (this.signUpForm.value.fullname === null || this.signUpForm.value.fullname === '') {
      return;
    }

    this.showLoadingIndicator = true;
    this.teacherLoginService.addStudent(this.signUpForm.value).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.CREATED) {
          // this.listStudent();
          this.signUpForm.reset();
          this.showLoadingIndicator = false;
          alert('Register is success')
        }
        if (kq.status === ServerResponseCode.EmailExist) {
          alert(kq.message)
        }
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  get f() {
    return this.signUpForm.controls;
  }

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
        console.log("kq: ", kq)
        if ((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority === role[2])) {
          this.teacherLoginService.setLogin(kq.data);
          this.router.navigate(['/']);
        } else if ((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority !== role[2])) {
          this.showLoadingIndicator = false;
          alert("You don't have role in system");
        } else {
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

    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
    
    this.signUpForm = this.formBuilder.group({
      username: [],
      email: [],
      password: [],
      fullname: [],
      role: [[role[2]]]
    })
  }

  ngOnChanges() {
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
  }

  ngAfterViewInit() {
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
  }
}
