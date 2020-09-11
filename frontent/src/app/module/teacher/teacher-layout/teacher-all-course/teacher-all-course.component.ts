import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { TeacherAllCourseService } from './teacher-all-course.service';
import { ServerResponseCode } from 'src/app/share/util/server-response-code.constant';
import { Observable } from 'rxjs';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';

export interface Course {
  coursename: string;
  description: string;
  totalStudentEnroll: number;
  createddate: string;
  updateddate: string;
  action: string;
}


@Component({
  selector: 'app-teacher-all-course',
  templateUrl: './teacher-all-course.component.html',
  styleUrls: ['./teacher-all-course.component.scss'],
  providers: [TeacherAllCourseService]
})
export class TeacherAllCourseComponent implements OnInit, AfterViewInit {
  public arrayCourse: any[] = [];
  @ViewChild('pageCourse', { static: false }) paginator: MatPaginator;
  displayedColumns: string[] = ['coursename', 'description', 'totalStudentEnroll', 'createddate', 'updateddate', 'action'];
  // dataSource: Observable<any[]> = new Observable();
  // dataSource: MatTableDataSource<any>;
  dataSource = new MatTableDataSource<any>();

  constructor(private teacherAllCourseService: TeacherAllCourseService,
              private router:Router) {
    // this.getCourse();
  }

  getCourse() {
    this.teacherAllCourseService.listCourses().subscribe(
      (res) => {
        console.log(res)
        if (res.status == ServerResponseCode.SUCCESS) {
          this.arrayCourse = res.data;
          this.dataSource = new MatTableDataSource(this.arrayCourse);
          this.dataSource.paginator = this.paginator
          // console.log(this.dataSource)
        }
      }
    )
  }

  viewCourse(id){
    this.router.navigate(['/teacher/detail-course/',id])
  }

  filterAllCourse(filterValue: string) {
    console.log('key: ', filterValue)

    let temp = this.arrayCourse.filter(e =>
      e.course.name.toLowerCase().includes(filterValue.trim().toLowerCase()) ||
      e.course.description.toLowerCase().includes(filterValue.trim().toLowerCase())
    );
    this.dataSource = new MatTableDataSource(temp);
  }

  ngOnInit() {
    this.getCourse();
  }

  ngAfterViewInit() {
  }
}
