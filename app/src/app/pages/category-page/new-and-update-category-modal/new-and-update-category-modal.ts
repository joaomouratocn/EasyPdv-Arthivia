import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CategoryResponseInterface } from '../../../models/interfaces/categoty-response-interface';
import { CategoryInsertModel } from '../../../models/models/category-insert-model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-and-update-category-modal',
  imports: [FormsModule],
  templateUrl: './new-and-update-category-modal.html',
  styleUrl: './new-and-update-category-modal.css',
})
export class NewAndUpdateCategoryModal {
  @Input() title: string = '';
  @Input() isOpen: boolean = false;

  @Output() saveCategory = new EventEmitter<CategoryInsertModel>();
  @Output() close = new EventEmitter<void>();

  @Input()
  set receivedCategory(value: CategoryResponseInterface) {
    if (value) {
      this.categoryName = value.name;
    } else {
      this.categoryName = '';
    }
  }

  categoryName: string = '';

  save() {
    console.log(this.receivedCategory);
    const category: CategoryInsertModel = this.receivedCategory
      ? { id: this.receivedCategory.id, name: this.categoryName }
      : { name: this.categoryName };

    this.saveCategory.emit(category);
  }

  closeModal() {
    this.close.emit();
  }
}
