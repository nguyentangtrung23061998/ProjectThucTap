import { Component, OnInit } from '@angular/core';
import { TeacherProfileService } from './teacher-profile.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/share/services/auth.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import {role} from '../../../../share/util/util.constant';
import * as $ from 'jquery';
@Component({
  selector: 'app-teacher-profile',
  templateUrl: './teacher-profile.component.html',
  styleUrls: ['./teacher-profile.component.scss'],
  providers:[TeacherProfileService]
})
export class TeacherProfileComponent implements OnInit {
  formProfile:FormGroup;
  formChangePassword:FormGroup;
  public submitted = false;
  public userObj = this.authService.userAccount;
  public userObjNew={
    username:null,
    email:null,
    fullname:null
  };
  public fileToUpload : File;
  public urlUpload = api_url.upload; 

  public edit = true;

  constructor(private profileService:TeacherProfileService,
              private authService : AuthService,
              private formBuilder : FormBuilder) { }

  getUser(){
    this.profileService.getUserById(this.userObj.id).subscribe(
      (kq:any)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.userObjNew = kq.data;
          console.log(this.userObjNew)
          this.formProfile= this.formBuilder.group({
            fullname:[this.userObjNew.fullname],
            email:[this.userObjNew.email],
            username:[this.userObjNew.username],
            role:[this.userObj.role.authority]
          })
          // console.log(this.userObjNew)
        }
      }
    )
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  editProfile(){
    this.edit= false;
  }

  updateProfile(){
    this.submitted = true;
    console.log(this.formProfile.value)
    this.profileService.update(this.formProfile.value,this.userObj.id).subscribe(
      (kq)=>{
        console.log(kq)
        if(kq.status === ServerResponseCode.SUCCESS){
          alert('Update profile is sucess');
          this.edit=true;
          
          this.getUser();
        }
      },error=>{
        
        console.log(error)
      }
    )
  }

  cancel(){
    this.edit=true;
  }

  uploadAvatar(){
    if (this.fileToUpload !== null) {
      this.profileService.uploadFile(this.fileToUpload, this.userObj.id).subscribe(
        (kq) => {
          console.log(kq)
          const valueFile = <HTMLInputElement>document.getElementById('file');
          valueFile.value = "";
          this.getUser();
        }, error => {
          console.log(error)
        }
      )
    }
  }

  onChangePassword(){
    this.profileService.changePassword(this.formChangePassword.value).subscribe(
      (kq)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          alert('Change password is success');
          this.formChangePassword.reset();
          $('#modelPassword').trigger('click');
        }
        if(kq.status === ServerResponseCode.ERROR){
          alert(kq.message);
        }
      }
    )
  }
  
  ngOnInit() {
    this.getUser();
    this.formProfile= this.formBuilder.group({
      fullname:[],
      email:[],
      username:[],
      role:[this.userObj.role.authority]
    });
    this.formChangePassword = this.formBuilder.group({
      currentPassword:[],
      newPassword:[]
    })
  }
}
