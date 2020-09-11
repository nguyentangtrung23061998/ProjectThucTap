import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherLoginComponent } from './teacher-login.component';
import { TeacherLoginRoutingModule } from './teacher-login-routing.module';
import {
  MatInputModule,
  MatFormFieldModule,
  MatButtonModule,
  MatToolbarModule,
  MatCardModule,
  MatMenuModule,
  MatIconModule,
  MatTableModule,
  MatDividerModule,
  MatSlideToggleModule,
  MatSelectModule,
  MatOptionModule,
  MatProgressSpinnerModule
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [
    TeacherLoginComponent
  ],
  imports: [
    CommonModule,
    TeacherLoginRoutingModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatDividerModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatProgressSpinnerModule,
    FormsModule, 
    ReactiveFormsModule
  ]
})
export class TeacherLoginModule { }
