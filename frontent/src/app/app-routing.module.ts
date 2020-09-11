import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path:'admin',
    loadChildren:() => import(`./module/admin/admin.module`).then(m => m.AdminModule)
  },
  {
    path:'teacher',
    loadChildren:() => import(`./module/teacher/teacher.module`).then(m => m.TeacherModule)
  },
  {
    path:'',
    loadChildren:() => import(`./module/student/student.module`).then(m=>m.StudentModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
