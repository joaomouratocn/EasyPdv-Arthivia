import { ChangeDetectorRef, Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryResponseInterface } from '../../models/interfaces/categoty-response-interface';
import { CategoryService } from '../../services/category-service';
import { CommonModule } from '@angular/common';
import { NewAndUpdateCategoryModal } from './new-and-update-category-modal/new-and-update-category-modal';
import { CategoryEditModel } from '../../models/models/category-edit-model';

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
  selectedCategory: CategoryResponseInterface | null = null;
  selectedIndex = -1;

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
        this.open(this.selectedIndex);
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
    if (this.selectedIndex === -1) {
      this.selectedIndex = 0;
    }
    this.focusRow(this.selectedIndex);
  }

  focusRow(index: number) {
    this.selectedCategory = this.categoryList[index];
    this.rows.get(index)?.nativeElement.focus();
  }

  selectRow(index: number) {
    this.selectedIndex = index;
    this.focusRow(index);
  }

  open(index: number) {
    if (this.selectedIndex === -1) {
      window.alert('Selecione um categoria');
      return;
    }

    this.openModal = true;
  }

  close(result: boolean) {
    if (result) {
      this.loadTable();
    }
    this.openModal = false;
  }
}
