import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/share/services/auth.service';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';

@Injectable()
export class StudentAuthGaurdService implements CanActivate{
  constructor(private auth:AuthService,private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    console.log(this.auth)
    if(this.auth.isStudent){
      return true;
    }else{
      this.router.navigate(['/']);
    }
  }
}
