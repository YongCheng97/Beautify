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
				label: 'View Categories', icon: 'pi pi-fw pi-list',
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
				label: 'View Tags', icon: 'pi pi-fw pi-tags',
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
				label: 'View Service Providers', icon: 'pi pi-fw pi-user',
			},
			{
				label: 'Logout', icon: 'pi pi-fw pi-sign-out', routerLink: ['/index']
			},
		]
	}

}
