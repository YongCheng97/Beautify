import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit 
{
	@Output() 
	childEvent = new EventEmitter();	
	
	username: string;
	password: string;
	loginError: boolean;
	errorMessage: string;
	
	
	
	constructor()
	{
	
	}

	
	
	ngOnInit() 
	{
	}
	

}
