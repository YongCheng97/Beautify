import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
/* import { MatFormFieldModule } from '@angular/material/form-field';
 */import { DatePipe } from '@angular/common';

import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { MegaMenuModule } from 'primeng/megamenu';
import { DataViewModule } from 'primeng/dataview';
import { PanelModule } from 'primeng/panel';
import { InputTextModule } from 'primeng/inputtext';
import {ListboxModule} from 'primeng/listbox';
import {SpinnerModule} from 'primeng/spinner';
import {InputTextareaModule} from 'primeng/inputtextarea';
import { MultiSelectModule } from 'primeng/multiselect';
import {RatingModule} from 'primeng/rating';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { IndexComponent } from './index/index.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ViewAllBookingSalesForUsComponent } from './view-all-booking-sales-for-us/view-all-booking-sales-for-us.component';
import { ViewAllPurchasedLineItemSalesForUsComponent } from './view-all-purchased-line-item-sales-for-us/view-all-purchased-line-item-sales-for-us.component';
import { ViewAllTagsComponent } from './view-all-tags/view-all-tags.component';
import { ViewAllServiceProvidersComponent } from './view-all-service-providers/view-all-service-providers.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    IndexComponent,
    MainMenuComponent,
    MainPageComponent,
    ViewAllBookingSalesForUsComponent,
    ViewAllPurchasedLineItemSalesForUsComponent,
    ViewAllTagsComponent,
    ViewAllServiceProvidersComponent

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
/*     MatFormFieldModule, */
    DropdownModule,
    MegaMenuModule,
    DataViewModule,
    PanelModule,
    InputTextModule,
    ListboxModule,
    SpinnerModule,
    InputTextareaModule,
    MultiSelectModule,
    RatingModule, 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
