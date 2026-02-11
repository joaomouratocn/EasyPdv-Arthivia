import { ChangeDetectorRef, Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryResponseInterface } from '../../models/interfaces/categoty-response-interface';
import { CategoryService } from '../../services/category-service';
import { CommonModule, NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-category-page',
  imports: [FormsModule, CommonModule, NgOptimizedImage],
  templateUrl: './category-page.html',
  styleUrl: './category-page.css',
})
export class CategoryPage {
  @ViewChildren('row') rows!: QueryList<ElementRef<HTMLTableRowElement>>;
  categorySearch: string = '';
  categoryList: CategoryResponseInterface[] = [];
  selectedIndex = 0;

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

  onTableFocus() {
    this.focusRow(this.selectedIndex);
  }

  focusRow(index: number) {
    this.rows.get(index)?.nativeElement.focus();
  }

  selectRow(index: number) {
    this.selectedIndex = index;
    this.focusRow(index);
  }

  onKeyDown(event: KeyboardEvent, index: number) {
    switch (event.key) {
      case 'ArrowDown':
        event.preventDefault();
        this.moveDown(index);
        break;

      case 'ArrowUp':
        event.preventDefault();
        this.moveUp(index);
        break;

      case 'Enter':
        event.preventDefault();
        this.edit(this.categoryList[index]);
        break;
    }
  }

  moveDown(index: number) {
    if (index < this.rows.length - 1) {
      this.selectedIndex = index + 1;
      this.focusRow(this.selectedIndex);
    }
  }

  moveUp(index: number) {
    if (index > 0) {
      this.selectedIndex = index - 1;
      this.focusRow(this.selectedIndex);
    }
  }

  onEnter(index: number) {
    const item = this.categoryList[index];
    this.edit(item);
  }

  edit(category: CategoryResponseInterface) {
    console.log('EDIT');
  }

  remove(category: CategoryResponseInterface) {
    console.log('REMOVE');
  }
}
