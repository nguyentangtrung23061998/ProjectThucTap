import { Component, OnInit } from '@angular/core';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TeacherMyCourseService } from './teacher-my-course.service';
import { AuthService } from 'src/app/share/services/auth.service';

@Component({
  selector: 'app-teacher-my-course',
  templateUrl: './teacher-my-course.component.html',
  styleUrls: ['./teacher-my-course.component.scss'],
  providers: [TeacherMyCourseService]
})
export class TeacherMyCourseComponent implements OnInit {
  public arrayCourse: any[] = [];
  public userObj = this.authService.userAccount;
  filter: String;
  public urlUpload = api_url.upload;
  public courseId = 0;

  public config = {
    itemsPerPage: 6,
    currentPage: 1,
    totalItems: this.arrayCourse.length
  }
  // filterAllCourse(filterValue: string) {
  //   console.log('key: ', filterValue)

  //   let temp = this.arrayCourse.filter(e =>
  //     e.course.name.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
  //     e.course.description.toLowerCase().includes(filterValue.trim().toLowerCase())
  //   );
  //   this.dataSource = new MatTableDataSource(temp);
  // }
 
  getCourseByUser() {
    console.log(this.userObj.id)
    this.courseService.getCourseByUserId(this.userObj.id).subscribe(
      (res) => {
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayCourse = res.data;
        }
      }
    )
  }

  getIdCourse(id){
    this.courseId = id;
  }

  detailCourse(){
    $('#modalOption').trigger('click');
    this.router.navigate(['/teacher/detail-course/',this.courseId])
  }

  updateCourse(){
    $('#modalOption').trigger('click');
    this.router.navigate(['/teacher/handle-course/',this.courseId])
  }

  deleteCourse(){
    this.courseService.delete(this.courseId).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.getCourseByUser();
          $('#modalOption').trigger('click');
        }
      }, (error) => {
        console.log("error: ", error)
      }
    )
  }
  constructor(
    private courseService: TeacherMyCourseService,
    private router: Router,
    private authService: AuthService) {
    // this.formCourse = this.formBuilder.group({
    //   name: [''],
    //   description: [''],
    //   active: [true, Validators.required]
    // })
  }
  ngOnInit() {
    this.getCourseByUser();
  }

}
