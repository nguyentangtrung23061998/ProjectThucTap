import { Component, OnInit, Inject, AfterViewInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as Util from '../../../../share/util/util.constant';
import { ManagerStudentService } from './manager-student.service';
import { Student } from './manager-student.interface'
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { AuthService } from 'src/app/share/services/auth.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-manager-student',
  templateUrl: './manager-student.component.html',
  styleUrls: ['./manager-student.component.scss'],
  providers: [ManagerStudentService]
})
export class ManagerStudentComponent implements OnInit {
  public arrayStudent: any[] = [];
  submitted = false;
  public _isAdd = true;
  private idStudent = 0;
  formStudent: FormGroup;
  filter:string;
  constructor(private studentService: ManagerStudentService,
    private formBuilder: FormBuilder,
    private authService: AuthService,) {

  }

  public config = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: this.arrayStudent.length
  }

  onPageChange(event) {
    this.config.currentPage = event;
  }

  get f(){
    return this.formStudent.controls;
  }

  ngOnInit() {
    this.listStudent();
    if (this._isAdd) {
      this.formStudent = this.formBuilder.group({
        email: ['', [ Validators.email]],
        password: [''],
        username: [''],
        fullname: [''],
        role: [[Util.roleRegister[2]], Validators.required]
      })
    } else {
      this.formStudent = this.formBuilder.group({
        email: ['', [Validators.email]],
        fullname: [''],
        role: [[Util.roleRegister[2]], Validators.required]
      })
    }
  }

  showModelAdd() {
    this._isAdd = true;

    this.formStudent = this.formBuilder.group({
      email: ['', [Validators.email]],
      password: [''],
      username: [''],
      fullname: [''],
      role: [[Util.roleRegister[2]], Validators.required]
    })
  }

  listStudent() {
    this.studentService.listStudent().subscribe(
      (res) => {
        console.log("list student show", res)
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayStudent = res.data;
        }
      }
    )
  }

  onSubmit() {
    this.submitted = true;
    const diabledButton = <HTMLInputElement>document.getElementById('btnStudent');

    if(this.formStudent.value.email === null || this.formStudent.value.email === ''){
      return;
    }else if(this.formStudent.value.username === null || this.formStudent.value.username === ''){
      return;
    }else if(this.formStudent.value.password === null || this.formStudent.value.password === ''){
      return;
    }
    else if(this.formStudent.value.fullname === null || this.formStudent.value.fullname === ''){
      return;
    }

    if (!this.formStudent.valid) {
      return;
    }

    if (this._isAdd) {
      if(this.formStudent.value.password === null || this.formStudent.value.password === ''){
        return;
      }
      this.studentService.addStudent(this.formStudent.value).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.CREATED) {
            this.listStudent();
            this.formStudent.reset();
            $('#modalStudent').trigger('click');
            this.submitted=false;
          }
          if(kq.status === ServerResponseCode.EmailExist){
            alert(kq.message)
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    } else {
      // console.log(this.formStudent.value);
      this.studentService.update(this.formStudent.value, this.idStudent).subscribe(
        (kq) => {
          console.log(kq)
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.listStudent();
            this.formStudent.reset();
            $('#modalStudent').trigger('click');
            this.submitted=false;
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    }
  }

  updateStudent(id) {
    this._isAdd = false;
    this.studentService.getStudent(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.formStudent = this.formBuilder.group({
            username:[kq.data.username],
            email: [kq.data.email, [Validators.email]],
            fullname: [kq.data.fullname],
            role: [Util.role[2]]
          })
          this.idStudent = id;
        }
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  deleteStudent(id){
    this.studentService.delete(id).subscribe(
      (kq)=>{
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.listStudent();
        }
      },(error)=>{
        console.log("error: ", error)
      }
    )
  }
}