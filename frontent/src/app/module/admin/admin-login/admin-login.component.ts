import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/share/services/auth.service';
import { Router } from '@angular/router';
import { AdminLoginService } from './admin-login.service';
import { Response } from 'src/app/share/services/http.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import * as $ from 'jquery';
import {role} from '../../../share/util/util.constant'
@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss'],
  providers: [AdminLoginService]
})
export class AdminLoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;

  showLoadingIndicator = false;

  public messageError: string = null;

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private adminLoginService: AdminLoginService) { }
  
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
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
    // if (this.authService.getUnauthorizedCookie() != null && this.authService.getUnauthorizedCookie().trim() !== '') {
    //   this.messageError = this.authService.getUnauthorizedCookie();
    //   this.authService.deleteUnauthorizedCookie();
    // }
  }

  get f() { return this.loginForm.controls; }



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
    this.adminLoginService.login(this.loginForm.value).subscribe(
      (kq: Response) => {
        console.log("kq: ", kq)
        // if (kq.status === ServerResponseCode.SUCCESS) {
        //   this.adminLoginService.setLogin(kq.data);
        //   this.router.navigate(['/admin']);
        // } else {
        //   this.showLoadingIndicator = false;
        //   if (diabledButton != null) {
        //     diabledButton.disabled = false;
        //   }
        //   this.messageError = kq.message;
        // }
        if ((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority === role[0])) {
          this.adminLoginService.setLogin(kq.data);
          this.router.navigate(['/admin']);
        } else if ((kq.status === ServerResponseCode.SUCCESS) && (kq.data.role.authority !== role[0])) {
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
}

