import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllPurchasedLineItemSalesForUsComponent } from './view-all-purchased-line-item-sales-for-us.component';

describe('ViewAllPurchasedLineItemSalesForUsComponent', () => {
  let component: ViewAllPurchasedLineItemSalesForUsComponent;
  let fixture: ComponentFixture<ViewAllPurchasedLineItemSalesForUsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllPurchasedLineItemSalesForUsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllPurchasedLineItemSalesForUsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
