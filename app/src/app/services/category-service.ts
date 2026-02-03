import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private httpClient: HttpClient) {}

  insertCategory(categoryName: String): Observable<SuccessResponse> {
    const path = 'category/insert';
    return this.httpClient.post<SuccessResponse>(
      path,
      { categoryName: categoryName },
      { withCredentials: true },
    );
  }
}
