import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentLayoutComponent } from './student-layout/student-layout.component';
import { StudentComponent } from './student.component';
import { StudentRoutingModule } from './student-routing.module';
import { CookieService } from 'ngx-cookie-service';
import { StudentAuthenticationComponent } from './student-authentication/student-authentication.component';

@NgModule({
  declarations: [StudentComponent],
  imports: [
    CommonModule,
    StudentRoutingModule
  ],
  providers: [CookieService],
})
export class StudentModule { }
