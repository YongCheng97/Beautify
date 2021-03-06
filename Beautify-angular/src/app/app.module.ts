import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DatePipe } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { MegaMenuModule } from 'primeng/megamenu';
import { DataViewModule } from 'primeng/dataview';
import { PanelModule } from 'primeng/panel';
import { InputTextModule } from 'primeng/inputtext';
import { ListboxModule } from 'primeng/listbox';
import { SpinnerModule } from 'primeng/spinner';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MultiSelectModule } from 'primeng/multiselect';
import { RatingModule } from 'primeng/rating';
import { FileUploadModule } from 'primeng/fileupload';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { PasswordModule } from 'primeng/password';

import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FooterComponent } from './footer/footer.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { HeaderComponent } from './header/header.component';
import { ViewAllReviewsComponent } from './view-all-reviews/view-all-reviews.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { ViewAllPurchasedLineItemsComponent } from './view-all-purchased-line-items/view-all-purchased-line-items.component';
import { ViewAllServicesComponent } from './view-all-services/view-all-services.component';
import { ViewAllProductsComponent } from './view-all-products/view-all-products.component';
import { ViewAllBookingSalesRecordComponent } from './view-all-booking-sales-record/view-all-booking-sales-record.component';
import { ViewAllPurchasedLineItemSalesRecordComponent } from './view-all-purchased-line-item-sales-record/view-all-purchased-line-item-sales-record.component';
import { ViewAllPromotionsComponent } from './view-all-promotions/view-all-promotions.component';

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
    ViewAllPurchasedLineItemsComponent,
    ViewAllBookingsComponent,
    ViewAllBookingSalesRecordComponent,
    ViewAllPurchasedLineItemSalesRecordComponent,
    ViewAllPromotionsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    ButtonModule,
    TableModule,
    BrowserAnimationsModule,
    CalendarModule,
    NgxMaterialTimepickerModule,
    MatFormFieldModule,
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
    FileUploadModule,
    MessageModule,
    MessagesModule,
    PasswordModule,
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
