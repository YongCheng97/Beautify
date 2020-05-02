import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { ViewAllServicesComponent } from './view-all-services/view-all-services.component';
import { ViewAllProductsComponent } from './view-all-products/view-all-products.component';
import { ViewAllReviewsComponent } from './view-all-reviews/view-all-reviews.component';
import { ViewAllPurchasedLineItemsComponent } from './view-all-purchased-line-items/view-all-purchased-line-items.component';
import { ViewAllBookingSalesRecordComponent } from './view-all-booking-sales-record/view-all-booking-sales-record.component';
import { ViewAllPurchasedLineItemSalesRecordComponent } from './view-all-purchased-line-item-sales-record/view-all-purchased-line-item-sales-record.component';

const routes: Routes = [
  { path: '', redirectTo: '/index', pathMatch: 'full' },
  { path: 'index', component: IndexComponent },
  { path: 'main-page', component: MainPageComponent},
  { path: 'sign-up', component: SignUpComponent},
  { path: 'view-all-bookings', component: ViewAllBookingsComponent},
  { path: 'view-all-services', component: ViewAllServicesComponent},
  { path: 'view-all-products', component: ViewAllProductsComponent},
  { path: 'view-all-reviews', component: ViewAllReviewsComponent},
  { path: 'view-all-purchased-line-items', component: ViewAllPurchasedLineItemsComponent},
  { path: 'view-all-booking-sales-record', component: ViewAllBookingSalesRecordComponent},
  { path: 'view-all-purchased-line-item-sales-record', component: ViewAllPurchasedLineItemSalesRecordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
