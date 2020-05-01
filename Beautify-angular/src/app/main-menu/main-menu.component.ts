import { Component, OnInit } from '@angular/core';

import { SessionService } from '../session.service';
import {MegaMenuItem,MenuItem} from 'primeng/api';


@Component({
	selector: 'app-main-menu',
	templateUrl: './main-menu.component.html',
	styleUrls: ['./main-menu.component.css']
})

export class MainMenuComponent implements OnInit {

	items: MegaMenuItem[];
	constructor(public sessionService: SessionService) {
	}

	ngOnInit() {
		this.items = [
            {
				label: 'View Own Profile',
			},
			{
				label: 'View All Listings', 
			}
		]
	}

}
