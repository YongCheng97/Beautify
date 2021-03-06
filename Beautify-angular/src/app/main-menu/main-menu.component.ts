import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MegaMenuItem, MenuItem } from 'primeng/api';


@Component({
	selector: 'app-main-menu',
	templateUrl: './main-menu.component.html',
	styleUrls: ['./main-menu.component.css']
})

export class MainMenuComponent implements OnInit {

	items: MegaMenuItem[];

	constructor(private router: Router,
		private activatedRoute: ActivatedRoute,) {
	}

	ngOnInit() {
		this.items = [
			{
				label: 'Home', icon: 'pi pi-fw pi-home', routerLink: ['/main-page']
			},
			{
				label: 'View Listings', icon: 'pi pi-fw pi-bars',
				items: [
					[
						{
							items: [{ label: 'Products' , routerLink: ['/view-all-products']}]
						},
						{
							items: [{ label: 'Services' , routerLink: ['/view-all-services']}]
						},
					],
				]
			},
			{
				label: 'View Reviews', icon: 'pi pi-fw pi-thumbs-up', routerLink: ['/view-all-reviews']
			},
			{
				label: 'View Bookings', icon: 'pi pi-fw pi-calendar', routerLink: ['/view-all-bookings']
			},
			{
				label: 'View Orders', icon: 'pi pi-fw pi-dollar', routerLink: ['/view-all-purchased-line-items']
			},
			{
				label: 'View Sales Record', icon: 'pi pi-fw pi-chart-bar',
				items: [
					[
						{
							items: [{ label: 'Products' , routerLink: ['/view-all-purchased-line-item-sales-record']}]
						},
						{
							items: [{ label: 'Services' , routerLink: ['/view-all-booking-sales-record']}]
						},
					],
				]
			},
			{
				label: 'View Promotions', icon: 'pi pi-fw pi-dollar', routerLink: ['/view-all-promotions']
			},
			{
				label: 'Logout', icon: 'pi pi-fw pi-sign-out', routerLink: ['/index']
			},
		]
	}

}
