import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Service } from './service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  baseUrl: string = "/api/Service";

  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  getServices(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllServices?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }

  createService(newService: Service, categoryId: number, tagIds: number[]): Observable<any> {
    let createServiceReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "service": newService,
      "categoryId": categoryId,
      "tagIds": tagIds
    }

    return this.httpClient.put<any>(this.baseUrl, createServiceReq, httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  updateService(serviceToUpdate: Service, categoryId: number, tagIds: number[]): Observable<any> {

    let updateBookReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "service": serviceToUpdate,
      "categoryId": categoryId,
      "tagIds": tagIds
    };

    return this.httpClient.post<any>(this.baseUrl, updateBookReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );
  }

  deleteService(serviceId: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrl + "/" + serviceId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
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
