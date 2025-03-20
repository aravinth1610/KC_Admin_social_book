import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  constructor(private httpClient: HttpClient) {}

  books(): Observable<any | HttpErrorResponse> {
    return this.httpClient.get(
      "http://localhost:8080/books",
      {
        withCredentials: true,
        observe: 'body',
      }
    );
  }
}
