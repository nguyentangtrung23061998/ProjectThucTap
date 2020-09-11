import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ManagerCourseService } from './manager-course.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import * as $ from 'jquery';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-course',
  templateUrl: './manager-course.component.html',
  styleUrls: ['./manager-course.component.scss'],
  providers: [ManagerCourseService]
})
export class ManagerCourseComponent implements OnInit, AfterViewInit {
  @ViewChild('takeInput', { static: false })
  InputVar: ElementRef;

  public arrayCourse: any[] = [];
  public urlUpload = api_url.upload;
  formCourse: FormGroup;
  public _isAdd = true;
  private idCourse = 0;
  public submitted = false;
  fileToUpload: File = null;

  filter:String;

  public config = {
    itemsPerPage: 6,
    currentPage: 1,
    totalItems: this.arrayCourse.length
  }

  onPageChange(event) {
    this.config.currentPage = event;
  }

  public chooseActiveArr: any[] = [
    { 'chooseName': 'Active', 'functionActive': true },
    { 'chooseName': 'No Active', 'functionActive': false },
  ];

  get f() {
    return this.formCourse.controls;
  }

  resetForm() {
    this.formCourse = this.formBuilder.group({
      name: [''],
      description: [''],
      active: [true, Validators.required]
    })
  }

  showModelAdd() {
    this._isAdd = true;
    this.formCourse.reset();
    this.formCourse.patchValue({
      active: true
    })
  }

  getCourse() {
    this.courseService.listCourses().subscribe(
      (res) => {
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayCourse = res.data;
          console.log(this.arrayCourse)
        }
      }
    )
  }
  
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  onSubmit() {

    this.submitted = true;
    if (this.formCourse.value.name === null || this.formCourse.value.name === '') {
      return;
    }
    if (this._isAdd) {
      this.courseService.addCourse(this.formCourse.value).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idCourse = kq.data.course.id;
            if (this.fileToUpload !== null) {
              this.courseService.uploadAvatar(this.fileToUpload, this.idCourse).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getCourse();
                  this.resetForm();
                }, error => {
                  console.log(error)
                }
              )
            } else {
              this.getCourse();
              this.resetForm();
            }

            // this.formCourse.reset();
            $('#modalCourse').trigger('click');
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    } else {
      this.courseService.update(this.formCourse.value, this.idCourse).subscribe(
        (kq) => {
          console.log(kq)
          if (kq.status === ServerResponseCode.SUCCESS) {
            if (this.fileToUpload !== null) {
              this.courseService.uploadAvatar(this.fileToUpload, this.idCourse).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getCourse();
                  this.formCourse.reset();
                }, error => {
                  console.log(error)
                }
              )
            } else {
              this.getCourse();
              this.resetForm();
            }
            $('#modalCourse').trigger('click');
          }
        }, (error: any) => {
          console.log("error: ", error)
        }
      )
    }
  }

  updateCourse(id) {
    this._isAdd = false;
    this.courseService.getCourse(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
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

  deleteCourse(id) {
    this.courseService.delete(id).subscribe(
      (kq) => {
        console.log(kq)
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getCourse();
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }

  detailCourse(id){
    this.router.navigate(['/admin/course/',id])
  }

  constructor(private formBuilder: FormBuilder,
    private courseService: ManagerCourseService,
    private router :Router) {
    this.formCourse = this.formBuilder.group({
      name: [''],
      description: [''],
      active: [true, Validators.required]
    })
  }


  ngOnInit() {
    this.getCourse();

  }
  ngAfterViewInit() {
    // this.InputVar.nativeElement.value = ""; 
  }
}
