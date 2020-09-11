import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { AuthService } from 'src/app/share/services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { role } from '../../../../share/util/util.constant';
import * as $ from 'jquery';
import { api_url } from '../../../../config/api_url.config';
import { ConfirmedValidator } from './confirm.validator';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [ProfileService]
})
export class ProfileComponent implements OnInit {
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
  fileToUpload: File = null;
  get f(){
    return this.formChangePassword.controls;
  }


  constructor(private profileService: ProfileService,
    private authService: AuthService,
    private formBuilder: FormBuilder) { }

  //   if(this.fileToUpload !== null) {
  //   this.lectureDetailService.uploadFile(this.fileToUpload, this.lectureId).subscribe(
  //     (kq) => {
  //       const valueFile = <HTMLInputElement>document.getElementById('file2');
  //       valueFile.value = "";
  //       this.getLectureObj(this.lectureId);
  //       this.video.nativeElement.src = this.urlUpload + '/videos/' + kq.data.video;
  //       this.video.nativeElement.load();
  //       this.video.nativeElement.play();
  //     }, error => {
  //       console.log(error)
  //     }
  //   )
  // }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
   
  }

  uploadAVatar(){
    if (this.fileToUpload !== null) {
      this.profileService.uploadFile(this.fileToUpload, this.userObj.id).subscribe(
        (kq) => {
          const valueFile = <HTMLInputElement>document.getElementById('file');
          valueFile.value = "";
          this.getUser();
        }, error => {
          console.log(error)
        }
      )
    }
  }

  getUser() {
    this.profileService.getUserById(this.userObj.id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.userObjNew = kq.data;
          console.log(this.userObjNew)
          this.formProfile = this.formBuilder.group({
            fullname: [this.userObjNew.fullname],
            email: [this.userObjNew.email],
            username: [this.userObjNew.username],
            role: [role[0]]
          })
          // console.log(this.userObjNew)
        }
      }
    )
  }

  editProfile() {
    this.edit = false;
  }

  updateProfile() {
    console.log(this.formProfile.value)
    this.profileService.update(this.formProfile.value, this.userObj.id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          // alert('Update profile is sucess');
          this.edit = true;
          this.getUser();
        }
      }, error => {
        console.log(error)
      }
    )
  }

  cancel() {
    this.edit = true;
  }

  onChangePassword() {
    this.profileService.changePassword(this.formChangePassword.value).subscribe(
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

  ngOnInit() {
    this.getUser();
    this.formProfile = this.formBuilder.group({
      fullname: [],
      email: [],
      username: [],
      role: [role[0]]
    });
    this.formChangePassword = this.formBuilder.group({
      currentPassword: [],
      newPassword: [,Validators.required],
      confirmPassword:[]
    },{
      validator: ConfirmedValidator('newPassword', 'confirmPassword')
    })
  }

}
