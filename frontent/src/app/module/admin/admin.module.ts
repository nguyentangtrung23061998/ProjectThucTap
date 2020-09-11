import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { CookieService } from 'ngx-cookie-service';
import { ComponentsModule } from 'src/app/component/components.module';
import { ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [AdminComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
  ],
  providers: [CookieService],
})
export class AdminModule { }
