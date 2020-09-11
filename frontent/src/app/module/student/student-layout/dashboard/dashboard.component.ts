import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { DashboardService } from './dashboard.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { api_url } from '../../../../config/api_url.config';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers:[DashboardService]
})
export class DashboardComponent implements OnInit {
  public arrayCourse:any[] = [];
  public arrayTeacher:any[] = [];
  public urlUpload = api_url.upload;

  carouselOptions = {
    margin: 25,
    nav: true,
    navText: ["<div class='nav-btn prev-slide'></div>", "<div class='nav-btn next-slide'></div>"],
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
        nav: true
      },
      600: {
        items: 1,
        nav: true
      },
      1000: {
        items: 2,
        nav: true,
        loop: false
      },
      1500: {
        items: 3,
        nav: true,
        loop: false
      }
    }
  }

  constructor(private dashBoardService : DashboardService) { }

  getAllTeacher(){
    this.dashBoardService.listTeacher().subscribe(
      (kq:any)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.arrayTeacher = kq.data;
        }
      },(error:any)=>{
        console.log(error)
      }
    )
  }

  getAllCourse(){
    this.dashBoardService.listCourses().subscribe(
      (kq:any)=>{
        if(kq.status === ServerResponseCode.SUCCESS){
          this.arrayCourse = kq.data;
        }
      },error=>{
        console.log(error)
      }
    )
  }

  ngOnInit() {
    this.getAllCourse();
    this.getAllTeacher();
  }

}
