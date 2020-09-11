import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { StudentComponent } from './student.component';
import { StudentLayoutComponent } from './student-layout/student-layout.component';
import { StudentAuthenticationComponent } from './student-authentication/student-authentication.component';
import { StudentAuthGaurdService } from './student-auth-gaurd.service';

const routes: Routes = [
  {
    path:'',
    loadChildren:()=>import(`./student-layout/student-layout.module`).then(m=>m.StudentLayoutModule)
  },
  {
    path:'authentication',
    loadChildren:()=>import(`./student-authentication/student-authentication.module`).then(m=>m.StudentAuthenticationModule),
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentRoutingModule { }
