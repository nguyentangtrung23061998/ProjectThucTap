import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterCoursePipe } from './student-layout.pipe';
import { StudentLayoutComponent } from './student-layout.component';
import { StudentLayoutRoutingModule } from './student-layout-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import {
  MatButtonModule,
  MatInputModule,
  MatFormFieldModule,
  MatMenuModule,
  MatTableModule,
  MatTooltipModule,
  MatPaginatorModule,
  MatCardModule,
  MatRadioModule,
  MatIconModule
} from '@angular/material';
import { OwlModule } from 'ngx-owl-carousel';
import { AllCouresComponent } from './all-courses/all-coures.component';
import { MycourseComponent } from './mycourse/mycourse.component';
import { ProfileComponent } from './profile/profile.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { DetailCourseComponent } from './detail-course/detail-course.component';
import { EnrollmentComponent } from './enrollment/enrollment.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { VideoCourseComponent } from './video-course/video-course.component';
import { QuizLectureComponent } from './quiz-lecture/quiz-lecture.component';
@NgModule({
  declarations: [FilterCoursePipe,
    StudentLayoutComponent,
    DashboardComponent,
    AllCouresComponent,
    MycourseComponent,
    ProfileComponent,
    ChangePasswordComponent,
    DetailCourseComponent,
    EnrollmentComponent,
    VideoCourseComponent,
    QuizLectureComponent],
  imports: [
    CommonModule,
    StudentLayoutRoutingModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    OwlModule,
    MatMenuModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatCardModule,
    MatRadioModule,
    MatIconModule,
  ]
})
export class StudentLayoutModule { }
