import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllProductCategoriesComponent } from './view-all-product-categories.component';

describe('ViewAllProductCategoriesComponent', () => {
  let component: ViewAllProductCategoriesComponent;
  let fixture: ComponentFixture<ViewAllProductCategoriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllProductCategoriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllProductCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
