import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLoginComponent } from './admin-login.component';
import { MatButtonModule, MatInputModule, MatFormFieldModule } from '@angular/material';
import { AdminLoginRoutingModule } from './admin-login-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [AdminLoginComponent],
  imports: [
    CommonModule,
    AdminLoginRoutingModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    FormsModule, 
    ReactiveFormsModule 
  ]
})
export class AdminLoginModule { }
