import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SuccessResponse } from '../models/interfaces/SuccessResponse';
import { Observable } from 'rxjs';
import { CategoryResponseInterface } from '../models/interfaces/categoty-response-interface';
import { CategoryEditModel } from '../models/models/category-edit-model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private httpClient: HttpClient) {}

  insertCategory(categoryName: String): Observable<SuccessResponse> {
    const path = 'api/category/insert';
    console.log(categoryName);
    return this.httpClient.post<SuccessResponse>(path, { categoryName: categoryName });
  }

  updateCategory(category: CategoryEditModel): Observable<SuccessResponse> {
    const path = `api/category/update/${category.id}`;
    return this.httpClient.post<SuccessResponse>(path, CategoryEditModel);
  }

  getAllCategories(): Observable<CategoryResponseInterface[]> {
    const path = 'api/category';
    return this.httpClient.get<CategoryResponseInterface[]>(path);
  }
}
