import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherComponent } from './teacher.component';
import { Routes, RouterModule } from '@angular/router';
import { TeacherAuthGaurdService } from './teacher-auth-gaurd.service';

const routes: Routes = [
  {
    path:'',component:TeacherComponent,
    children:[
      {
        path:'login',
        loadChildren:() => import(`./teacher-login/teacher-login.module`).then(m => m.TeacherLoginModule)
      },
      {
        path:'',
        loadChildren:() => import(`./teacher-layout/teacher-layout.module`).then(m => m.TeacherLayoutModule),
        canActivate:[TeacherAuthGaurdService]
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],  
  providers:[TeacherAuthGaurdService]
})
export class TeacherRoutingModule { }
