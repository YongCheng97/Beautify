import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';

import { AppRoutingModule } from './app-routing.module';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { MegaMenuModule } from 'primeng/megamenu';
import { CarouselModule } from 'primeng/carousel';

import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';

import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FooterComponent } from './footer/footer.component'; 
import { MainMenuComponent } from './main-menu/main-menu.component'; 
import { HeaderComponent } from './header/header.component'; 
import { ViewAllReviewsComponent } from './view-all-reviews/view-all-reviews.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { ViewAllServicesComponent } from './view-all-services/view-all-services.component';
import { ViewAllProductsComponent } from './view-all-products/view-all-products.component';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    MainPageComponent,
    SignUpComponent,
    FooterComponent,
    MainMenuComponent,
    HeaderComponent,
    ViewAllReviewsComponent, 
    ViewAllBookingsComponent, 
    ViewAllServicesComponent,
    ViewAllProductsComponent, 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    DialogModule,
    ButtonModule,
    TableModule,
    BrowserAnimationsModule,
    CalendarModule,
    NgxMaterialTimepickerModule,
    MatFormFieldModule,
    MegaMenuModule,
    CarouselModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
