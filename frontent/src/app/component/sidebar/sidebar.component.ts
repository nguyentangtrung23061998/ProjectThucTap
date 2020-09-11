import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/share/services/auth.service';

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/admin/student', title: 'Manager Student',  icon: 'person', class: '' },
    { path: '/admin/teacher', title: 'Manager Teacher',  icon:'device_hub', class: '' },
    { path: '/admin/course', title: 'Manager Course',  icon:'book', class: '' },
    { path: '/admin/lecture', title: 'Manager Lecture',  icon:'library_books', class: '' },
    { path: '/admin/question', title: 'Manager Question Bank',  icon:'question_answer', class: '' },
    { path: '/admin/profile', title: 'Profile',  icon:'perm_identity', class: '' },
    { path: '/admin/Logout', title: 'Logout',  icon:'login', class: '' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  providers:[AuthService]
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(private router:Router,
            private authService:AuthService) { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }

  activeLogout(title){
    if(title === 'Logout'){
      this.authService.logOut();
      this.router.navigate(['/admin/login'])
    }
  }

  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };
}
