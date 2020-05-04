import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
};

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  baseUrl: string = "/api/File";

  constructor(private httpClient: HttpClient) { }


  uploadFile(fileToUpload: File): Observable<any>
	{
    let formData: FormData = new FormData();   
    formData.append('file', fileToUpload, fileToUpload.name);
		
		return this.httpClient.post<any>(this.baseUrl + '/upload', formData).pipe
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
