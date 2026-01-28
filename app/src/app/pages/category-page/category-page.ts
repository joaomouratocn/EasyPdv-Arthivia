import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryResponseInterface } from '../../models/interfaces/CategoryResponseInterface';

@Component({
  selector: 'app-category-page',
  imports: [FormsModule],
  templateUrl: './category-page.html',
  styleUrl: './category-page.css',
})
export class CategoryPage {
  categorySearch: string = '';
  categoryList: CategoryResponseInterface[] = [];
}
