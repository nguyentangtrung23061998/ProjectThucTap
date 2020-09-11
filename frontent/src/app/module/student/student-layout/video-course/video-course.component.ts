import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { VideoCourseService } from './video-course.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { MatTableDataSource } from '@angular/material';
import { api_url } from '../../../../config/api_url.config';
import { AuthService } from 'src/app/share/services/auth.service';
@Component({
  selector: 'app-video-course',
  templateUrl: './video-course.component.html',
  styleUrls: ['./video-course.component.scss'],
  providers:[VideoCourseService]
})
export class VideoCourseComponent implements OnInit {
  @ViewChild('video', { static: false })
  public video: ElementRef;
  public courseObj = {
    course:{},
    totalStudentEnroll:0,
    user:{}
  };
  public courseVideo = {};
  public arrLecture:any[] = [];
  displayedColumnsLecture: string[] = ['name', 'description', 'image', 'createddate','action'];
  dataSourceLecture = new MatTableDataSource<any>();
  public urlUpload = api_url.upload;

  filterAllLecture(filterValue) {
    let temp = this.arrLecture.filter(e =>
      e.name.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.description.toLowerCase().includes(filterValue.trim().toLowerCase())
    );
    this.dataSourceLecture = new MatTableDataSource(temp);
  }

  constructor(private videoCourseService: VideoCourseService,
              private route:ActivatedRoute,
              private authService :AuthService,
              private router : Router) { }

  getCourseById(id){
    this.videoCourseService.getCourse(id).subscribe(
      (kq: any)=>{
        if(kq.status=== ServerResponseCode.SUCCESS){
          this.courseObj = kq.data;
          this.arrLecture = kq.data.course.lectures;
          this.dataSourceLecture = new MatTableDataSource(this.arrLecture);
        }
      },(error)=>{
        console.log(error)
      }
    )
  }
  startQuiz(id){
    this.router.navigate(['/quiz-lecture/',id]);
  }

  viewCourse(id){
    this.videoCourseService.getLecture(id).subscribe(
      (kq:any)=>{
        console.log(kq)
        if(kq.status === ServerResponseCode.SUCCESS){
          this.courseVideo = kq.data;
          this.video.nativeElement.src =this.urlUpload + '/videos/'+ kq.data.video;
          this.video.nativeElement.load();
        }
      }
    )
  }
  ngOnInit() {
    this.authService.isStudent;
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.getCourseById(id);
  }

}
