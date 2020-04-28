import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SignUpComponent } from './sign-up/sign-up.component';

import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';

import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    MainPageComponent,
    SignUpComponent,
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
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
