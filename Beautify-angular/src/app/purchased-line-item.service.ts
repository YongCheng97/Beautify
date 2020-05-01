import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { PurchasedLineItem } from './purchased-line-item';



const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};



@Injectable({
  providedIn: 'root'
})

export class PurchasedLineItemService 
{
	baseUrl: string = "/api/PurchasedLineItem";
	
	
	
	constructor(private httpClient: HttpClient,
				private sessionService: SessionService)
	{		
	}
	
	
	
	getPurchasedLineItems(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllPurchasedLineItems?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}
	
	
	
	getPurchasedLineItemByPurchasedLineItemId(purchasedLineItemId: number): Observable<any>
	{
		return this.httpClient.get<any>(this.baseUrl + "/retrievePurchasedLineItem/" + purchasedLineItemId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}	
	
	
	updatePurchasedLineItem(purchasedLineItemToUpdate: PurchasedLineItem): Observable<any>
	{
		let updatePurchasedLineItemReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"purchasedLineItem": purchasedLineItemToUpdate,
		};
		
		return this.httpClient.post<any>(this.baseUrl, updatePurchasedLineItemReq, httpOptions).pipe
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
