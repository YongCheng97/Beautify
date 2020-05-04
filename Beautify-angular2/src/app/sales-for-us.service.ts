import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { SalesForUs } from './sales-for-us';



const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};



@Injectable({
  providedIn: 'root'
})

export class SalesForUsService 
{
	baseUrl: string = "/api/SalesForUs";
	
	
	
	constructor(private httpClient: HttpClient,
				private sessionService: SessionService)
	{		
	}
	
	
	
	getBookingSalesRecords(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSalesRecordBooking?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}	
	
	getPurchasedLineItemSalesRecords(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSalesRecordPurchasedLineItem?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}	
	
	getServiceProviderSalesRecords(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSalesRecordServiceProvider?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}	
	
	
	private handleError(error: HttpErrorResponse)
	{
		let errorMessage: string = "";
		
		if (error.error instanceof ErrorEvent) 
		{		
			errorMessage = "An unknown error has occurred: " + error.error.message;
		} 
		else 
		{		
			errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
		}
		
		console.error(errorMessage);
		
		return throwError(errorMessage);		
	}
}

