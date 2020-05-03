import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Product } from './product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  baseUrl: string = "/api/FileUpload";

  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  uploadImage(uploadedFiles: any[], productToUpload: Product): Observable<any> {

    let uploadFilesReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "uploadedFiles": uploadedFiles,
      "product": productToUpload,
    };

    return this.httpClient.put<any>(this.baseUrl, uploadFilesReq, httpOptions).pipe(
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
