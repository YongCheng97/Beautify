import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllPurchasedLineItemsComponent } from './view-all-purchased-line-items.component';

describe('ViewAllPurchasedLineItemsComponent', () => {
  let component: ViewAllPurchasedLineItemsComponent;
  let fixture: ComponentFixture<ViewAllPurchasedLineItemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllPurchasedLineItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllPurchasedLineItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
