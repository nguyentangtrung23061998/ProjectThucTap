import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentAuthenticationComponent } from './student-authentication.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', component: StudentAuthenticationComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentAuthenticationRoutingModule { }
