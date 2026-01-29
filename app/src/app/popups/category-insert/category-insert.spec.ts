import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryInsert } from './category-insert';

describe('CategoryInsert', () => {
  let component: CategoryInsert;
  let fixture: ComponentFixture<CategoryInsert>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryInsert]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryInsert);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
