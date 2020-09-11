import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/share/services/auth.service';
import { api_url } from 'src/app/config/api_url.config';

@Component({
  selector: 'app-teacher-layout',
  templateUrl: './teacher-layout.component.html',
  styleUrls: ['./teacher-layout.component.scss']
})
export class TeacherLayoutComponent implements OnInit {
  private authUser;
  public urlUpload = api_url.upload;
  constructor(private router: Router,
    private authService: AuthService) { }

  routerAllCourse() {
    this.router.navigate(['/teacher/all-course'])
  }

  routerMyCourse() {
    this.router.navigate(['/teacher/mycourse'])
  }


  createCourse() {
    this.router.navigate(['/teacher/handle-course'])
  }

  signOut() {
    this.authService.logOut();
    this.router.navigate(['/teacher/login'])
  }

  ngOnInit() {
    this.authUser = this.authService.userAccount;
  }

}
