import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryResponseInterface } from '../../models/interfaces/categoty-response-interface';
import { CategoryService } from '../../services/category-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-page',
  imports: [FormsModule, CommonModule],
  templateUrl: './category-page.html',
  styleUrl: './category-page.css',
})
export class CategoryPage {
  categorySearch: string = '';
  categoryList: CategoryResponseInterface[] = [];

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.categoryService.getAllCategories().subscribe({
      next: (result) => {
        this.categoryList = result;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err?.error.message);
      },
    });
  }
}
