import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AdminLayoutComponent } from './admin-layout.component';
import { ManagerStudentComponent } from './manager-student/manager-student.component';
import { ManagerTeacherComponent } from './manager-teacher/manager-teacher.component';
import { ManagerCourseComponent } from './manager-course/manager-course.component';
import { ManagerCourseDetailComponent } from './manager-course-detail/manager-course-detail.component';
import { ManagerLectureComponent } from './manager-lecture/manager-lecture.component';
import { ManagerLectureDetailComponent } from './manager-lecture-detail/manager-lecture-detail.component';
import { ManagerQuestionComponent } from './manager-question/manager-question.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  {
    path: '', component: AdminLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'student'
      },
      {
        path:'teacher',
        component:ManagerTeacherComponent
      },
      {
        path:'student',
        component:ManagerStudentComponent
      },
      {
        path:'course',
        component:ManagerCourseComponent
      },
      {
        path:'course/:id',
        component:ManagerCourseDetailComponent
      },
      {
        path:'lecture',
        component:ManagerLectureComponent
      },
      {
        path:'lecture/:id',
        component:ManagerLectureDetailComponent
      },
      {
        path:'question',
        component:ManagerQuestionComponent
      },
      {
        path:'profile',
        component:ProfileComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminLayoutRoutingModule { }
