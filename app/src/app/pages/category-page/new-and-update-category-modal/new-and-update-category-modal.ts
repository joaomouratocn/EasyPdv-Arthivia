import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CategoryResponseInterface } from '../../../models/interfaces/categoty-response-interface';
import { CategoryEditModel } from '../../../models/models/category-edit-model';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../../services/category-service';

@Component({
  selector: 'app-new-and-update-category-modal',
  imports: [FormsModule],
  templateUrl: './new-and-update-category-modal.html',
  styleUrl: './new-and-update-category-modal.css',
})
export class NewAndUpdateCategoryModal {
  @Input() title: string = '';
  @Input() isOpen: boolean = false;

  //@Output() saveCategory = new EventEmitter<CategoryInsertModel>();
  @Output() close = new EventEmitter<boolean>();

  @Input()
  set receivedCategory(value: CategoryResponseInterface | null) {
    if (value) {
      this.categoryName = value.name;
    } else {
      this.categoryName = '';
    }
  }

  constructor(private categoryService: CategoryService) {}

  categoryName: string = '';

  save() {
    if (this.categoryName === '') {
      window.alert('Nome invalído!');
      return;
    }

    if (this.receivedCategory?.id) {
      const editedCategory = new CategoryEditModel(this.receivedCategory.id, this.categoryName);
      this.categoryService.updateCategory(editedCategory).subscribe({
        next(result) {
          window.alert(result.message);
        },
        error(err) {
          window.alert(err?.error.message);
        },
      });
    } else {
      this.categoryService.insertCategory(this.categoryName).subscribe({
        next(result) {
          window.alert(result.message);
        },
        error(err) {
          window.alert(err?.error.message);
        },
      });
    }

    this.closeModal(true);
  }

  closeModal(result: boolean) {
    this.close.emit(result);
  }
}
