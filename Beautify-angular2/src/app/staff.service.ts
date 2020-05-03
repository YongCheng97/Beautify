import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Staff } from './staff';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
	providedIn: 'root'
})

export class StaffService {

	baseUrl: string = "/api/Staff";

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {

	}

	login(username: string, password: string): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/staffLogin?username=" + username + "&password=" + password).pipe(
			catchError(this.handleError)
		)
	}

	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = "";

		if (error.error instanceof ErrorEvent) {
			errorMessage = error.error.message;
		}
		else {
			errorMessage = error.error.message + " Please try again!";
		}

		console.error(errorMessage);

		return throwError(errorMessage);
	}

}
