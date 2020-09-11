import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { TeacherLayoutComponent } from './teacher-layout.component';
import { TeacherAuthGaurdService } from '../teacher-auth-gaurd.service';
import { TeacherAllCourseComponent } from './teacher-all-course/teacher-all-course.component';
import { TeacherAllCourseDetailComponent } from './teacher-all-course-detail/teacher-all-course-detail.component';
import { TeacherMyCourseComponent } from './teacher-my-course/teacher-my-course.component';
import { TeacherAllCourserHandleCourseComponent } from './teacher-all-course-handle-course/teacher-all-courser-handle-course.component';
import { TeacherDetailLectureComponent } from './teacher-detail-lecture/teacher-detail-lecture.component';
import { TeacherProfileComponent } from './teacher-profile/teacher-profile.component';
import { TeacherChangePasswordComponent } from './teacher-change-password/teacher-change-password.component';

const routes: Routes = [
  {
    path:'',component:TeacherLayoutComponent,
    children:[
      {
        path: '',
        redirectTo: 'all-course'
      },
      {
        path :'all-course',
        component:TeacherAllCourseComponent
      },
      {
        path:'detail-course/:id',
        component:TeacherAllCourseDetailComponent
      },
      {
        path:'mycourse',
        component:TeacherMyCourseComponent
      },
      {
        path:'handle-course',
        component:TeacherAllCourserHandleCourseComponent
      },
      {
        path:'handle-course/:id',
        component:TeacherAllCourserHandleCourseComponent
      },
      {
        path:'detail-lecture/:id',
        component:TeacherDetailLectureComponent
      },
      {
        path:'profile',
        component:TeacherProfileComponent
      },
      {
        path:'change-password',
        component:TeacherChangePasswordComponent
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeacherLayoutRoutingModule { }
