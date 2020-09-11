import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { AdminAuthGaurdService } from './admin-auth-gaurd.service';

const routes: Routes = [
  {
    path:'',component:AdminComponent,
    children:[
      {
        path:'login',
        loadChildren:() => import(`./admin-login/admin-login.module`).then(m => m.AdminLoginModule)
      },
      {
        path:'',
        loadChildren:()=>import(`./admin-layout/admin-layout.module`).then(m=>m.AdminLayoutModule),
        canActivate:[AdminAuthGaurdService]
      }
    ]
  }
]


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers:[AdminAuthGaurdService]
})
export class AdminRoutingModule { }
