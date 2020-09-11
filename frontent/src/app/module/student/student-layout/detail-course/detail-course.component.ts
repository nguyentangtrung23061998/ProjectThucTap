import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { api_url } from '../../../../config/api_url.config';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { DetailCourseService } from './detail-course.service';
import { AuthService } from 'src/app/share/services/auth.service';
import { FormBuilder } from '@angular/forms';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-detail-course',
  templateUrl: './detail-course.component.html',
  styleUrls: ['./detail-course.component.scss'],
  providers:[DetailCourseService]
})
export class DetailCourseComponent implements OnInit {
  @ViewChild('pageLecture', { static: false }) paginator: MatPaginator;
  @ViewChild('pageQuestion', { static: false }) paginatorQuestion: MatPaginator;
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
                        
  displayedColumnsLecture: string[] = ['name', 'description', 'image', 'createddate'];
  displayedColumnsQuestion: string[] = ['question', 'answerA', 'answerB', 'answerC', 'answerD', 'correct'];
  dataSourceLecture = new MatTableDataSource<any>();
  dataSourceQuestion = new MatTableDataSource<any>();

  constructor(private route: ActivatedRoute,
    private router : Router,
    private courseDetailService: DetailCourseService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private cookieService : CookieService) { }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  getCourseId(id) {
    this.courseDetailService.getCourse(id).subscribe(
      (kq) => {
        console.log(kq)
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.courseObj = kq.data;
          this.courseInfo = kq.data.course;
          this.totalStudent = kq.data.totalStudentEnroll;
          this.userInfo = kq.data.user;
          this.arrLecture = kq.data.course.lectures;
          this.dataSourceLecture = new MatTableDataSource(this.arrLecture);
          this.dataSourceLecture.paginator = this.paginator
        
        }
      }, (error) => {
        console.log(error)
      }
    )
  }
  veiewDetailLecture(id){
    this.router.navigate(['/teacher/detail-lecture/',id]);
  }

  enrollment(id){
   console.log(this.userAuth)
    if(this.userAuth != undefined){
      let user = this.userAuth;
      this.courseDetailService.enrollmentCourse(user.id,id).subscribe(
        (kq: any)=>{
          console.log(kq)
          if(kq.status === ServerResponseCode.SUCCESS && kq.data!== null){
           alert('Enrollment is success')
          }
          if(kq.data === null){
            alert(kq.message)
          }
        },(error)=>{
          console.log(error)
          alert('Enrollment is fail')
        }
      )
    }else{
      this.router.navigate(["/authentication"])
    }
  }

  ngOnInit() {
    this.authService.isStudent;
    let obj = this.cookieService.get('userAccount')
    if(obj.length>0){
      this.userAuth =JSON.parse(obj);
    }
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.courseId = id;
    this.getCourseId(id);
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

}
