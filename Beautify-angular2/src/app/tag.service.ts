import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Tag } from './tag';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class TagService {

  baseUrl: string = "/api/Tag";

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
  }

  getTags(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllTagsForStaff?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }
  
  createTag(newTag: Tag): Observable<any>
  {
	  let createTagReq = {
		  "username": this.sessionService.getUsername(),
		  "password": this.sessionService.getPassword(),
		  "newTag": newTag 
		  };
	  
	  return this.httpClient.put<any>(this.baseUrl, createTagReq, httpOptions).pipe
	  (
		catchError(this.handleError)
	  );
  }
  
  deleteTag(tagId: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrl + "/" + tagId + "?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
      (
        catchError(this.handleError)
      );
  }
  
  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";

    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occurred: " + error.error.message;
    }
    else {
      errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }
}

