import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { StudentLayoutComponent } from './student-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AllCouresComponent } from './all-courses/all-coures.component';
import { MycourseComponent } from './mycourse/mycourse.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProfileComponent } from './profile/profile.component';
import { DetailCourseComponent } from './detail-course/detail-course.component';
import { EnrollmentComponent } from './enrollment/enrollment.component';
import { VideoCourseComponent } from './video-course/video-course.component';
import { QuizLectureComponent } from './quiz-lecture/quiz-lecture.component';
import { StudentAuthGaurdService } from '../student-auth-gaurd.service';


const routes: Routes = [
  {
    path: '', component: StudentLayoutComponent,
    children:[
      {
        path:'',
        component:DashboardComponent
      },
      {
        path:'all-courses',
        component:AllCouresComponent
      },
      {
        path:'mycourse',
        component:MycourseComponent,
        canActivate:[StudentAuthGaurdService]
      },
      {
        path:'profile',
        component:ProfileComponent
      },
      {
        path:'change-password',
        component:ChangePasswordComponent
      },
      {
        path:'detail-courses/:id',
        component:DetailCourseComponent
      },
      {
        path:'enrollment/:courseId',
        component:EnrollmentComponent
      },
      {
        path:'video-course/:id',
        component:VideoCourseComponent
      },
      {
        path:'quiz-lecture/:id',
        component:QuizLectureComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers:[StudentAuthGaurdService]
})
export class StudentLayoutRoutingModule { }
