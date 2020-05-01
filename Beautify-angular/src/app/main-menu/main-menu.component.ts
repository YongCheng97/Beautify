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
							items: [{ label: 'Products' }]
						},
						{
							items: [{ label: 'Services' }]
						},
					],
				]
			},
			{
				label: 'View Reviews', icon: 'pi pi-fw pi-thumbs-up',
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
							items: [{ label: 'Products' }]
						},
						{
							items: [{ label: 'Services' }]
						},
					],
				]
			},
			{
				label: 'View Profile', icon: 'pi pi-fw pi-user',
			},
			{
				label: 'Logout', icon: 'pi pi-fw pi-sign-out', routerLink: ['/index']
			},
		]
	}

}
