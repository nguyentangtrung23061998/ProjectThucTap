import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { api_url } from '../../../../config/api_url.config';
import { TeacherAllCourseService } from '../teacher-all-course/teacher-all-course.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TeacherAllCourserHandleCourseService } from './teacher-all-courser-handle-course.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import * as $ from 'jquery';
@Component({
  selector: 'app-teacher-all-courser-handle-course',
  templateUrl: './teacher-all-courser-handle-course.component.html',
  styleUrls: ['./teacher-all-courser-handle-course.component.scss'],
  providers: [TeacherAllCourserHandleCourseService]
})
export class TeacherAllCourserHandleCourseComponent implements OnInit {
  @ViewChild('takeInput', { static: false })
  InputVar: ElementRef;

  public arrayCourse: any[] = [];
  public urlUpload = api_url.upload;
  formCourse: FormGroup;
  public _isAdd = true;
  private idCourse = 0;
  public submitted = false;
  public courseObj = {};
  fileToUpload: File = null;

  filter: String;

  public chooseActiveArr: any[] = [
    { 'chooseName': 'Active', 'functionActive': true },
    { 'chooseName': 'No Active', 'functionActive': false },
  ];

  constructor(private formBuilder: FormBuilder,
    private teacherAllCourseService: TeacherAllCourserHandleCourseService,
    private router: Router,
    private route: ActivatedRoute) { }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  onSubmit() {
    this.submitted = true;
    // const diabledButton = <HTMLInputElement>document.getElementById('btnCourse');

    // if (diabledButton != null) {
    //   diabledButton.disabled = true;
    // }
    
    if (this.formCourse.value.name === null || this.formCourse.value.description === '') {
      return;
    }
    if (this._isAdd) {
      this.teacherAllCourseService.addCourse(this.formCourse.value).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idCourse = kq.data.course.id;
            if (this.fileToUpload !== null) {
              this.teacherAllCourseService.uploadAvatar(this.fileToUpload, this.idCourse).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  // valueFile.value = "";
                  alert('Create Course is success')
                  this.router.navigate(['/teacher/mycourse'])
                }, error => {
                  console.log(error)
                }
              )
            }else{
              alert('Create Course is success')
              this.router.navigate(['/teacher/mycourse'])
            }
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    } else {
      this.teacherAllCourseService.update(this.formCourse.value, this.idCourse).subscribe(
        (kq) => {
          console.log(kq)
          if (kq.status === ServerResponseCode.SUCCESS) {
            if (this.fileToUpload !== null) {
              this.teacherAllCourseService.uploadAvatar(this.fileToUpload, this.idCourse).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  // valueFile.value = "";
                  alert('Update Course is success')
                  this.router.navigate(['/teacher/mycourse'])
                }, error => {
                  console.log(error)
                }
              )
            }else{
              alert('Update Course is success')
              this.router.navigate(['/teacher/mycourse'])
            }
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    }

  }

  updateCourse(id) {
    this._isAdd = false;
    this.teacherAllCourseService.getCourse(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.courseObj = kq.data.course;
          this.formCourse = this.formBuilder.group({
            name: [kq.data.course.name],
            description: [kq.data.course.description],
            active: [kq.data.course.active]
          })
          this.idCourse = kq.data.course.id;
        }
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  ngOnInit() {
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    if (id === NaN) {
      this._isAdd = true;
    }
    if (id > 0) {
      this._isAdd = false;
      this.updateCourse(id);
    } else {
      this._isAdd = true;
    }
    console.log(this._isAdd)
    this.formCourse = this.formBuilder.group({
      name: [''],
      description: [''],
      active: [true, Validators.required]
    })
  }

}
