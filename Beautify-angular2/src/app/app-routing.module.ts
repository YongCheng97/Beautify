import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ViewAllBookingSalesForUsComponent } from './view-all-booking-sales-for-us/view-all-booking-sales-for-us.component';
import { ViewAllPurchasedLineItemSalesForUsComponent } from './view-all-purchased-line-item-sales-for-us/view-all-purchased-line-item-sales-for-us.component';
import { ViewAllTagsComponent } from './view-all-tags/view-all-tags.component';
import { ViewAllServiceProvidersComponent } from './view-all-service-providers/view-all-service-providers.component';
import { ViewAllProductCategoriesComponent } from './view-all-product-categories/view-all-product-categories.component';
import { ViewAllServiceCategoriesComponent } from './view-all-service-categories/view-all-service-categories.component';


const routes: Routes = [
	{ path: '', redirectTo: '/index', pathMatch: 'full' },
	{ path: 'index', component: IndexComponent },
	{ path: 'main-page', component: MainPageComponent},
	{ path: 'view-all-booking-sales-for-us', component: ViewAllBookingSalesForUsComponent},
	{ path: 'view-all-purchased-line-item-sales-for-us', component: ViewAllPurchasedLineItemSalesForUsComponent},
	{ path: 'view-all-tags', component: ViewAllTagsComponent},
	{ path: 'view-all-service-providers', component: ViewAllServiceProvidersComponent},
	{ path: 'view-all-product-categories', component: ViewAllProductCategoriesComponent},
	{ path: 'view-all-service-categories', component: ViewAllServiceCategoriesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
