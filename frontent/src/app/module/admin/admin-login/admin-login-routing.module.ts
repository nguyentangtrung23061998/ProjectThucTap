import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AdminLoginComponent } from './admin-login.component';

const routes: Routes = [
	{
		path: '', component: AdminLoginComponent
	}
];

@NgModule({
	declarations: [],
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class AdminLoginRoutingModule { }
