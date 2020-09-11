import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLayoutComponent } from './admin-layout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutingModule } from './admin-layout-routing.module';
import { ManagerStudentComponent } from './manager-student/manager-student.component';
import { ComponentsModule } from 'src/app/component/components.module';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import { ManagerTeacherComponent } from './manager-teacher/manager-teacher.component';
import { ManagerCourseComponent } from './manager-course/manager-course.component';
import { MatCardModule } from '@angular/material';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatIconModule} from '@angular/material/icon';
import { NgxPaginationModule } from 'ngx-pagination';
import {FilterCoursePipe,FilterLecturePipe,FilterQuestionPipe,FilterUserPipe} from './admin-layout.pipe';
import { ManagerCourseDetailComponent } from './manager-course-detail/manager-course-detail.component';
import {MatTableModule} from '@angular/material/table';
import { ManagerLectureComponent } from './manager-lecture/manager-lecture.component';
import { ManagerLectureDetailComponent } from './manager-lecture-detail/manager-lecture-detail.component';
import { ManagerQuestionComponent } from './manager-question/manager-question.component';
import { ProfileComponent } from './profile/profile.component';
@NgModule({
  declarations: [AdminLayoutComponent,
    ManagerStudentComponent, 
    ManagerTeacherComponent,
    ManagerCourseComponent,
    FilterCoursePipe,
    ManagerCourseDetailComponent,
    ManagerLectureComponent,
    FilterLecturePipe,
    ManagerLectureDetailComponent,
    ManagerQuestionComponent,
    FilterQuestionPipe,
    FilterUserPipe,
    ProfileComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AdminLayoutRoutingModule,
    ComponentsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatDialogModule,
    MatCardModule,
    MatProgressBarModule,
    MatIconModule,
    NgxPaginationModule,
    MatTableModule
  ]
})
export class AdminLayoutModule { }
