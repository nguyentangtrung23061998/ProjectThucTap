import { Component, OnInit } from '@angular/core';
import { MycourseService } from './mycourse.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/share/services/auth.service';

@Component({
  selector: 'app-mycourse',
  templateUrl: './mycourse.component.html',
  styleUrls: ['./mycourse.component.scss'],
  providers: [MycourseService]
})
export class MycourseComponent implements OnInit {
  public arrayCourse: any[] = [];
  public userObj;
  filter: String;
  public urlUpload = api_url.upload;
  public courseId = 0;
  public config = {
    itemsPerPage: 6,
    currentPage: 1,
    totalItems: this.arrayCourse.length
  }


  getCourseByUser() {
    let id = this.userObj.id;
    // debugger
    console.log('id: ', id)
    if (id > 0) {
      this.courseService.enrollmentCourse(id).subscribe(
        (res) => {
          console.log(res)
          if (res.status == ServerResponseCode.SUCCESS) {
            this.arrayCourse = res.data;
          }
        }, error => {
          console.log(error)
        }
      )
    }

  }

  getIdCourse(id) {
    this.router.navigate(['/video-course/', id])
  }

  detailCourse() {
    $('#modalOption').trigger('click');
    this.router.navigate(['/teacher/detail-course/', this.courseId])
  }

  updateCourse() {
    $('#modalOption').trigger('click');
    this.router.navigate(['/teacher/handle-course/', this.courseId])
  }

  deleteCourse() {
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
  constructor(private courseService: MycourseService,
    private router: Router,
    private authService: AuthService) { }

  ngOnInit() {
    // let check = this.authService.isStudent;
    //   console.log(this.authService.userAccount)
    // console.log(check)
    // if(check){
    // this.userObj = this.authService.userAccount;
    //   if(this.userObj !== null){
    //  this.getCourseByUser();
    //   }
    // }
    console.log(this.authService)
    this.userObj = this.authService;
    if (this.userObj.isStudent) {
      if (this.userObj != null) {
        this.userObj = this.userObj.userAccount;
        this.getCourseByUser();
      }
    }
  }

}
