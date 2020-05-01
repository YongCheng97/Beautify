import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Booking } from './booking';



const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};



@Injectable({
  providedIn: 'root'
})

export class BookingService 
{
	baseUrl: string = "/api/Booking";
	
	
	
	constructor(private httpClient: HttpClient,
				private sessionService: SessionService)
	{		
	}
	
	
	
	getBookings(): Observable<any>
	{				
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllBookings?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}
	
	
	
	getBookingByBookingId(bookingId: number): Observable<any>
	{
		return this.httpClient.get<any>(this.baseUrl + "/retrieveBooking/" + bookingId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
	}	
	
	
	updateBooking(bookingToUpdate: Booking): Observable<any>
	{
		let updateBookingReq = {
			"username": this.sessionService.getUsername(),
			"password": this.sessionService.getPassword(),
			"booking": bookingToUpdate,
		};
		
		return this.httpClient.post<any>(this.baseUrl, updateBookingReq, httpOptions).pipe
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
