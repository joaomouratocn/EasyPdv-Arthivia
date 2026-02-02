import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-category-insert',
  imports: [],
  templateUrl: './category-insert-pop.html',
  styleUrl: './category-insert-pop.css',
})
export class CategoryInsertPop {
  @Input() open = false;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<string>();

  name = '';
}
