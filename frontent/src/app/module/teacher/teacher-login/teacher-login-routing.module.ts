import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherLoginComponent } from './teacher-login.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
	{
		path: '', component: TeacherLoginComponent
	}
];


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class TeacherLoginRoutingModule { }
