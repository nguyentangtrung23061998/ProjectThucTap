import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'
import { ManagerCourseDetailService } from './manager-course-detail.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
import {Observable , of, from} from 'rxjs';

@Component({
  selector: 'app-manager-course-detail',
  templateUrl: './manager-course-detail.component.html',
  styleUrls: ['./manager-course-detail.component.scss'],
  providers: [ManagerCourseDetailService]
})
export class ManagerCourseDetailComponent implements OnInit, AfterViewInit {

  public courseObj = null;
  public totalStudent = 0;
  public urlUpload = api_url.upload;
  public arrLecture:any[]=[];
  displayedColumns: string[] = ['name', 'description','image', 'createddate'];
  dataSource: Observable<any[]> = new Observable();
 
  public userInfo = {
    id:null,
    username:null,
    email:null,
    image:null,
    fullname:null
  };
  public courseInfo= {
    id:null,
    name:null,
    description:null,
    image:null,
    lectures:[],
    active:null
  };

  constructor(private router: ActivatedRoute,
    private courseDetailService: ManagerCourseDetailService) {
   
  }

  getLectureByCourseId(id) {
    this.courseDetailService.getLectureByCourseId(id).subscribe(
      (kq: any) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.arrLecture = kq.data;
        }
      }, (error: any) => {
        console.log(error)
      }
    )
  }

  ngOnInit() {
    let id = parseInt(this.router.snapshot.paramMap.get('id'));
    this.courseDetailService.getCourse(id).subscribe(
      (kq) => {
        if (kq.status === ServerResponseCode.SUCCESS) {
          this.courseObj = kq.data;
          this.courseInfo = kq.data.course;
          this.totalStudent = kq.data.totalStudentEnroll;
          this.userInfo = kq.data.user;
          this.dataSource = kq.data.course.lectures
          console.log(this.courseInfo)
          console.log(this.userInfo)
        }
      }, (error) => {
        console.log(error)
      }
    )
  }

  ngAfterViewInit(){
    
  }

}
