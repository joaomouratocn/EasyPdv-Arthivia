import { ChangeDetectorRef, Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryResponseInterface } from '../../models/interfaces/categoty-response-interface';
import { CategoryService } from '../../services/category-service';
import { CommonModule } from '@angular/common';
import { NewAndUpdateCategoryModal } from './new-and-update-category-modal/new-and-update-category-modal';
import { CategoryInsertModel } from '../../models/models/category-insert-model';

@Component({
  selector: 'app-category-page',
  imports: [FormsModule, CommonModule, NewAndUpdateCategoryModal],
  templateUrl: './category-page.html',
  styleUrl: './category-page.css',
})
export class CategoryPage {
  openModal = false;
  @ViewChildren('row') rows!: QueryList<ElementRef<HTMLTableRowElement>>;
  categorySearch: string = '';
  categoryList: CategoryResponseInterface[] = [];
  selectedIndex = 0;

  constructor(
    private categoryService: CategoryService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.loadTable();
  }

  loadTable() {
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
        console.log(this.selectedCategory);
        this.open(true);
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

  open(exist: boolean) {
    if (exist) {
      this.selectedCategory = this.categoryList[this.selectedIndex];
    } else {
      this.selectedCategory = null;
    }
    this.openModal = true;
  }

  close() {
    this.openModal = false;
  }

  saveCategory(category: CategoryInsertModel) {
    this.close();
    console.log(category);
    if (category.id) {
      //implement update category
    } else {
      this.categoryService.insertCategory(category.name).subscribe({
        next: (result) => {
          console.log(result);
          this.loadTable();
        },
        error: (err) => {
          window.alert(err?.error.message);
        },
      });
    }
  }
}
