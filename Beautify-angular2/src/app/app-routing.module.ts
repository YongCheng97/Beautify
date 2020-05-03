import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ViewAllBookingSalesForUsComponent } from './view-all-booking-sales-for-us/view-all-booking-sales-for-us.component';
import { ViewAllPurchasedLineItemSalesForUsComponent } from './view-all-purchased-line-item-sales-for-us/view-all-purchased-line-item-sales-for-us.component';

const routes: Routes = [
	{ path: '', redirectTo: '/index', pathMatch: 'full' },
	{ path: 'index', component: IndexComponent },
	{ path: 'main-page', component: MainPageComponent},
	{ path: 'view-all-booking-sales-for-us', component: ViewAllBookingSalesForUsComponent},
	{ path: 'view-all-purchased-line-item-sales-for-us', component: ViewAllPurchasedLineItemSalesForUsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
