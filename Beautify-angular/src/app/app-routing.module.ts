import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './index/index.component';
import { MainPageComponent } from './main-page/main-page.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { ViewAllPurchasedLineItemsComponent } from './view-all-purchased-line-items/view-all-purchased-line-items.component';


const routes: Routes = [
  { path: '', redirectTo: '/index', pathMatch: 'full' },
  { path: 'index', component: IndexComponent },
  { path: 'main-page', component: MainPageComponent},
  { path: 'sign-up', component: SignUpComponent},
  { path: 'view-all-bookings', component: ViewAllBookingsComponent},
  { path: 'view-all-purchased-line-items', component: ViewAllPurchasedLineItemsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
