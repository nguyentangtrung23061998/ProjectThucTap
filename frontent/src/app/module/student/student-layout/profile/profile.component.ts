import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/share/services/auth.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import {role} from '../../../../share/util/util.constant';
import * as $ from 'jquery';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers:[ProfileService]
})
export class ProfileComponent implements OnInit {
  formProfile:FormGroup;
  formChangePassword:FormGroup;
  public userObj =null;
  public userObjNew={
    username:null,
    email:null,
    fullname:null,
    provider:null
  };
  public fileToUpload : File;
  public urlUpload = api_url.upload; 

  public edit = true;

  constructor(private profileService:ProfileService,
              private authService : AuthService,
              private formBuilder : FormBuilder) { }

  getUser(){
    this.profileService.getUserById(this.userObj.id).subscribe(
      (kq:any)=>{
        console.log(kq)
        if(kq.status === ServerResponseCode.SUCCESS){
          this.userObjNew = kq.data;
          this.formProfile= this.formBuilder.group({
            fullname:[this.userObjNew.fullname],
            email:[this.userObjNew.email],
            username:[this.userObjNew.username],
            role:[role[2]]
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
    console.log(this.formProfile.value)
    if(this.formProfile.value.username === null || this.formProfile.value.username === ''){
      alert('Username is require')
      return;
    }
    if(this.formProfile.value.fullname === null || this.formProfile.value.fullname === ''){
      alert('Fullname is require')
      return;
    }
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
    this.authService.isStudent;
    this.userObj = this.authService.userAccount;
    this.getUser();
    this.formProfile= this.formBuilder.group({
      fullname:[],
      email:[],
      username:[],
      role:[role[2]]
    });
    this.formChangePassword = this.formBuilder.group({
      currentPassword:[],
      newPassword:[]
    })
  }
}
