import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentAuthenticationComponent } from './student-authentication.component';
import { StudentAuthenticationRoutingModule } from './student-authentication-routing.module';
import { MatButtonModule, MatInputModule, MatFormFieldModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [StudentAuthenticationComponent],
  imports: [
    CommonModule,
    StudentAuthenticationRoutingModule,
    MatButtonModule, 
    MatInputModule, 
    MatFormFieldModule,
    FormsModule, 
    ReactiveFormsModule 
  ]
})
export class StudentAuthenticationModule { }
