import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TeacherAllCourseDetailService } from './teacher-all-course-detail.service';
import { api_url } from '../../../../config/api_url.config';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { AuthService } from 'src/app/share/services/auth.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as $ from 'jquery';


@Component({
  selector: 'app-teacher-all-course-detail',
  templateUrl: './teacher-all-course-detail.component.html',
  styleUrls: ['./teacher-all-course-detail.component.scss'],
  providers: [TeacherAllCourseDetailService]
})
export class TeacherAllCourseDetailComponent implements OnInit {
  @ViewChild('pageLecture', { static: false }) paginator: MatPaginator;
  @ViewChild('pageQuestion', { static: false }) paginatorQuestion: MatPaginator;
  formLecture: FormGroup;
  public courseObj = null;
  public totalStudent = 0;
  public urlUpload = api_url.upload;
  public userAuth = this.authService.userAccount;
  public arrLecture: any[] = [];
  public arrayQuestion: any[] = [];
  private isAuh = false;
  public _isAdd = true;
  public courseId = 0;
  public idLecture = 0;
  public submitted = false;
  fileToUpload: File = null;
  public userInfo = {
    id: null,
    username: null,
    email: null,
    image: null,
    fullname: null
  };
  public courseInfo = {
    id: null,
    name: null,
    description: null,
    image: null,
    lectures: [],
    active: null
  };
  displayedColumnsLecture: string[] = ['name', 'description', 'image', 'createddate', 'action'];
  displayedColumnsQuestion: string[] = ['question', 'answerA', 'answerB', 'answerC', 'answerD', 'correct'];
  dataSourceLecture = new MatTableDataSource<any>();
  dataSourceQuestion = new MatTableDataSource<any>();

  constructor(private router: ActivatedRoute,
    private routeNavigate: Router,
    private courseDetailService: TeacherAllCourseDetailService,
    private authService: AuthService,
    private formBuilder: FormBuilder) { }

  get f() {
    return this.formLecture.controls;
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);

  }

  resetForm() {
    this.formLecture = this.formBuilder.group({
      name: [''],
      description: [''],
      idCourse: [this.arrLecture[0].course.id]
    })
  }
  showModelAdd() {
    this._isAdd = true;

    this.formLecture = this.formBuilder.group({
      name: [''],
      description: ['']
    })
  }

  onSaveLecture() {
    this.submitted = true;
    if (this.formLecture.value.name === null || this.formLecture.value.name === '') {
      return;
    }
    if (this._isAdd) {
      this.courseDetailService.addLectureByCourseId(this.formLecture.value, this.courseId).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idLecture = kq.data.id;
            if (this.fileToUpload !== null) {
              this.courseDetailService.uploadFile(this.fileToUpload, this.idLecture).subscribe(
                (kq: any) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getCourseId(this.courseId)
                  $('#modalLecture').trigger('click');
                }, (error) => {
                  console.log(error)
                }
              )
            } else {
              this.getCourseId(this.courseId)
              $('#modalLecture').trigger('click');
            }

          }
        },
        (error) => {
          console.log(error)
        }
      )
    } else {
      this.courseDetailService.updateLecture(this.formLecture.value, this.idLecture).subscribe(
        (kq) => {
          if (kq.status === ServerResponseCode.SUCCESS) {
            this.idLecture = kq.data.id;
            if (this.fileToUpload !== null) {
              this.courseDetailService.uploadFile(this.fileToUpload, this.idLecture).subscribe(
                (kq) => {
                  this.submitted = false;
                  const valueFile = <HTMLInputElement>document.getElementById('file');
                  valueFile.value = "";
                  this.getCourseId(this.courseId)

                }, error => {
                  console.log(error)
                }
              )
            } else {
              this.getCourseId(this.courseId)
            }
            $('#modalLecture').trigger('click');
          }
        }, (error) => {
          console.log(error)
        }
      )
    }
  }

  updateLecture(id) {
    this._isAdd = false;
    this.courseDetailService.getLectureById(id).subscribe(
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

  getCourseId(id) {
    this.courseDetailService.getCourse(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.courseObj = kq.data;
          this.courseInfo = kq.data.course;
          this.totalStudent = kq.data.totalStudentEnroll;
          this.userInfo = kq.data.user;
          this.arrLecture = kq.data.course.lectures;
          this.dataSourceLecture = new MatTableDataSource(this.arrLecture);
          this.dataSourceLecture.paginator = this.paginator
          if (this.userAuth.email === this.userInfo.email) {
            this.isAuh = true;
          }
        }
      }, (error) => {
        console.log(error)
      }
    )
  }
  veiewDetailLecture(id) {
    this.routeNavigate.navigate(['/teacher/detail-lecture/', id]);
  }

  ngOnInit() {
    let id = parseInt(this.router.snapshot.paramMap.get('id'));
    this.courseId = id;
    this.getCourseId(id);

    if (this._isAdd) {
      this.formLecture = this.formBuilder.group({
        name: [''],
        description: [''],
        idCourse: []
      })
    } else {
      this.formLecture = this.formBuilder.group({
        name: [''],
        description: ['']
      })
    }

  }

  filterAllLecture(filterValue) {
    let temp = this.arrLecture.filter(e =>
      e.name.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.description.toLowerCase().includes(filterValue.trim().toLowerCase())
    );
    this.dataSourceLecture = new MatTableDataSource(temp);
  }

  filterAllQuestion(filterValue) {
    let temp = this.arrayQuestion.filter(e =>
      e.question.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.answerfirst.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.answersecond.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.answerthird.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.answerfourth.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.correctanswer.toLowerCase().includes(filterValue.trim().toLowerCase())
    );
    this.dataSourceQuestion = new MatTableDataSource(temp);
  }

  deleteLecture(id) {
    this.courseDetailService.deleteLecture(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getCourseId(this.courseId)
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }

  vewQuestion(id) {
    this.courseDetailService.listQuestionByLectureId(id).subscribe(
      (kq: any) => {
        console.log(kq)
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrayQuestion = kq.data;
          this.dataSourceQuestion = new MatTableDataSource(this.arrayQuestion);
          this.dataSourceQuestion.paginator = this.paginatorQuestion;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

}
