import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ManagerLectureService } from './manager-lecture.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import * as $ from 'jquery';

@Component({
  selector: 'app-manager-lecture',
  templateUrl: './manager-lecture.component.html',
  styleUrls: ['./manager-lecture.component.scss'],
  providers: [ManagerLectureService]
})
export class ManagerLectureComponent implements OnInit, AfterViewInit {
  selectCourse: FormGroup;
  formLecture: FormGroup;
  public arrayCourse: any[] = [];
  public arrayLecture: any[] = [];
  private idCourse: number = 0;
  private idLecture: number = 0;
  public _isAdd = true;
  public submitted = false;
  public urlUpload = api_url.upload;
  fileToUpload: File = null;
  filter:String;

  public config = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: this.arrayCourse.length
  }

  onPageChange(event) {
    this.config.currentPage = event;
  }

  constructor(private formBuilder: FormBuilder,
    private lectureService: ManagerLectureService,
    private router: Router) {
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
    console.log(this.fileToUpload)
  }

  resetForm() {
    this.formLecture = this.formBuilder.group({
      name: [''],
      description: [''],
      idCourse: [this.arrayCourse[0].course.id]
    })
  }

  get f() {
    return this.formLecture.controls;
  }

  showModelAdd() {
    this._isAdd = true;
    this.resetForm();
  }

  onSaveLecture() {
    this.submitted = true;
    this.idCourse = this.formLecture.value.idCourse;
    console.log(this.fileToUpload)
    if (this.formLecture.value.name === null || this.formLecture.value.name === '') {
      return;
    }
    if (this._isAdd) {
      this.lectureService.addLectureByCourseId(this.formLecture.value, this.idCourse).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idLecture = kq.data.id;
            if (this.fileToUpload !== null) {
              this.lectureService.uploadFile(this.fileToUpload, this.idLecture).subscribe(
                (kq: any) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getAllLecture();
                  this.resetForm();
                }, (error) => {
                  console.log(error)
                }
              )
            } else {
              this.getAllLecture();
              this.resetForm();
            }
            $('#modalLecture').trigger('click');
          }
        },
        (error) => {
          console.log(error)
        }
      )
    } else {
      console.log(this.idLecture)
      this.lectureService.updateLecture(this.formLecture.value, this.idLecture).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idLecture = kq.data.id;
            if (this.fileToUpload !== null) {
              this.lectureService.uploadFile(this.fileToUpload, this.idLecture).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getAllLecture();
                  this.resetForm();
                }, error => {
                  console.log(error)
                }
              )
            } else {
              this.getAllLecture();
              this.resetForm();
            }
            $('#modalLecture').trigger('click');
          }
        }, (error) => {
          console.log(error)
        }
      )
    }
  }

  onSubmit() {

  }

  detailLecture(id){
    this.router.navigate(['/admin/lecture/',id])
  }

  getCourse() {
    this.lectureService.listCourses().subscribe(
      (res) => {
        console.log(res)
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayCourse = res.data;
          this.idCourse = this.arrayCourse[0].course.id;
          this.selectCourse.patchValue({
            name: this.arrayCourse[0].course.id
          })
          this.formLecture.patchValue({
            idCourse: this.arrayCourse[0].course.id
          })
        }
      }
    )
  }

  getAllLecture() {
    this.lectureService.getAllLecture().subscribe(
      (kq) => {
        if (kq.status == ServerResponseCode.SUCCESS) {
          this.arrayLecture = kq.data;
        }
      }, (error: any) => {
        console.log()
      }
    )
  }

  getLectureByCourseId(id) {
    this.lectureService.getLectureByCourseId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrayLecture = kq.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  onChangeCourse() {
    console.log(this.selectCourse.value.name)
    this.getLectureByCourseId(this.selectCourse.value.name);
  }

  ngOnInit() {
    this.selectCourse = this.formBuilder.group({
      name: ['']
    })

    if (this._isAdd) {
      this.formLecture = this.formBuilder.group({
        name: [''],
        description: [''],
        idCourse: [this.idCourse]
      })
    } else {
      this.formLecture = this.formBuilder.group({
        name: [''],
        description: ['']
      })
    }
    this.getCourse();
    this.getAllLecture();
  }

  updateLecture(id) {
    this._isAdd = false;
    this.lectureService.getLectureById(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.formLecture = this.formBuilder.group({
            name: [kq.data.name],
            description: [kq.data.description]
          })
        }
        this.idLecture = kq.data.id;
      }, (error: any) => {
        console.log("error: ", error)
      }
    )
  }

  deleteLecture(id) {
    this.lectureService.deleteLecture(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getAllLecture();
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }

  ngAfterViewInit() {

  }
}
