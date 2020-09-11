import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as Util from '../../../../share/util/util.constant';
import { ManagerTeacherService } from './manager-teacher.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import * as $ from 'jquery';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-manager-teacher',
  templateUrl: './manager-teacher.component.html',
  styleUrls: ['./manager-teacher.component.scss'],
  providers: [ManagerTeacherService]
})
export class ManagerTeacherComponent implements OnInit {
  formTeacher: FormGroup
  public _isAdd = true;
  public arrayTeacher: any[] = [];
  public arrCourseInfo: any[] =[];
  submitted = false;
  private idTeacher = 0;
  filter: string;
  displayedColumns: string[] = ['course', 'description','totalStudentEnroll', 'createddate'];
  dataSource: Observable<any[]> = new Observable();

  constructor(private formBuilder: FormBuilder,
    private teacherService: ManagerTeacherService) { }

  public config = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: this.arrayTeacher.length
  }

  onPageChange(event) {
    this.config.currentPage = event;
  }

  showModelAdd() {
    this._isAdd = true;
    this.formTeacher = this.formBuilder.group({
      email: ['', [Validators.email]],
      password: [''],
      username: [''],
      fullname: [''],
      role: [[Util.roleRegister[1]], Validators.required]
    })
  }

  get f() {
    return this.formTeacher.controls;
  }

  getTeacher() {
    this.teacherService.listTeacher().subscribe(
      (res) => {
        console.log(res)
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayTeacher = res.data;
        }
      }
    )
  }

  ngOnInit() {
    this.getTeacher();
    if (this._isAdd) {
      this.formTeacher = this.formBuilder.group({
        email: ['', [Validators.email]],
        password: [''],
        username: [''],
        fullname: [''],
        role: [[Util.roleRegister[1]], Validators.required]
      })
    } else {
      this.formTeacher = this.formBuilder.group({
        email: ['', [Validators.email]],
        fullname: [''],
        role: [[Util.roleRegister[1]], Validators.required]
      })
    }
  }

  onSubmit() {
    console.log(this.formTeacher.value)
    this.submitted = true;

    if (this.formTeacher.value.email === null || this.formTeacher.value.email === '') {
      return;
    } else if (this.formTeacher.value.username === null || this.formTeacher.value.username === '') {
      return;
    } else if (this.formTeacher.value.password === null || this.formTeacher.value.password === '') {
      return;
    }
    else if (this.formTeacher.value.fullname === null || this.formTeacher.value.fullname === '') {
      return;
    }

    if (!this.formTeacher.valid) {
      return;
    }

    if (this._isAdd) {
      if (this.formTeacher.value.password === null || this.formTeacher.value.password === '') {
        return;
      }
      this.teacherService.addTeacher(this.formTeacher.value).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.CREATED) {
            this.getTeacher();
            this.formTeacher.reset();
            $('#modalTeacher').trigger('click');
            this.submitted = false;
          }
          console.log(kq)
          if (kq.status === ServerResponseCode.EmailExist) {
            alert(kq.message)
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    } else {
      this.teacherService.update(this.formTeacher.value, this.idTeacher).subscribe(
        (kq) => {
          console.log(kq)
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.getTeacher();
            this.formTeacher.reset();
            $('#modalTeacher').trigger('click');
            this.submitted = false;
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    }
  }

  updateTeacher(id) {
    this._isAdd = false;
    this.teacherService.getTeacherById(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.formTeacher = this.formBuilder.group({
            username: [kq.data.username],
            email: [kq.data.email, [Validators.email]],
            fullname: [kq.data.fullname],
            role: [Util.role[1], Validators.required]
          })
          this.idTeacher = id;
        }
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  viewCourse(id){
    this.teacherService.getCourseByTeacherId(id).subscribe(
      (kq)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.arrCourseInfo= kq.data;
          this.dataSource= kq.data;
        }
      },error=>{
        console.log(error)
      }
    )
  }

  deleteTeacher(id) {
    this.teacherService.delete(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getTeacher();
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }
}
