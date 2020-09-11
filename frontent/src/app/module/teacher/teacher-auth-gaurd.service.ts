import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/share/services/auth.service';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

@Injectable()
export class TeacherAuthGaurdService implements CanActivate{
  constructor(private auth:AuthService,private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if(this.auth.isTeacher){
      return true;
    }else{
      this.router.navigate(['/teacher/login']);
    }
  }
}
