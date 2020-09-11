import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherLayoutComponent } from './teacher-layout.component';
import { FilterCoursePipe } from './teacher-layout.pipe';
import { TeacherLayoutRoutingModule } from './teacher-layout-routing.module';
import {
  MatToolbarModule,
  MatIconModule,
  MatSidenavModule,
  MatListModule,
  MatButtonModule,
  MatMenuModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule,
  MatTooltipModule,
  MatPaginatorModule,
  MatSelectModule
} from '@angular/material';
import { TeacherAllCourseComponent } from './teacher-all-course/teacher-all-course.component';
import { TeacherAllCourseDetailComponent } from './teacher-all-course-detail/teacher-all-course-detail.component';
import { TeacherMyCourseComponent } from './teacher-my-course/teacher-my-course.component';
import { TeacherAllCourserHandleCourseComponent } from './teacher-all-course-handle-course/teacher-all-courser-handle-course.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { TeacherDetailLectureComponent } from './teacher-detail-lecture/teacher-detail-lecture.component';
import { TeacherProfileComponent } from './teacher-profile/teacher-profile.component';
import { TeacherChangePasswordComponent } from './teacher-change-password/teacher-change-password.component'
@NgModule({
  declarations: [TeacherLayoutComponent,
    FilterCoursePipe,
    TeacherAllCourseComponent,
    TeacherAllCourseDetailComponent,
    TeacherMyCourseComponent,
    TeacherAllCourserHandleCourseComponent,
    TeacherDetailLectureComponent, TeacherProfileComponent, TeacherChangePasswordComponent],
  imports: [
    CommonModule,
    TeacherLayoutRoutingModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatSelectModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class TeacherLayoutModule { }
