import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllServiceCategoriesComponent } from './view-all-service-categories.component';

describe('ViewAllServiceCategoriesComponent', () => {
  let component: ViewAllServiceCategoriesComponent;
  let fixture: ComponentFixture<ViewAllServiceCategoriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllServiceCategoriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllServiceCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
