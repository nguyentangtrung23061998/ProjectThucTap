import { Component, OnInit } from '@angular/core';
import { AllCouresService } from './all-coures.service';
import { api_url } from 'src/app/config/api_url.config';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { Router } from '@angular/router';
import * as $ from "jquery";
import { AuthService } from 'src/app/share/services/auth.service';

@Component({
  selector: 'app-all-coures',
  templateUrl: './all-coures.component.html',
  styleUrls: ['./all-coures.component.scss'],
  providers:[AllCouresService]
})
export class AllCouresComponent implements OnInit {
  public arrayCourse:any[] = [];
  public urlUpload = api_url.upload;
  public courseId = 0;
  public userAuth;
  public filter:string;

  constructor(private allCourseService: AllCouresService,
                private router : Router,
                private authService: AuthService) { }

  getIdCourse(id){
    this.courseId = id;
  }

  getAllCourse(){
    this.allCourseService.listCourses().subscribe(
      (kq:any)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.arrayCourse = kq.data;
        }
      },error=>{
        console.log(error)
      }
    )
  }

  detailCourse(){
    $('#modalOption').trigger('click');
    this.router.navigate(['/detail-courses/',this.courseId]);
  }

  enrollment(){
    if(this.userAuth != undefined){
      let user = this.userAuth;
      this.allCourseService.enrollmentCourse(user.id,this.courseId).subscribe(
        (kq: any)=>{
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
    this.getAllCourse();
    this.authService.isStudent;
    this.userAuth = this.authService.userAccount;
  }
}
