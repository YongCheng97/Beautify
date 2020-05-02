import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllPurchasedLineItemSalesRecordComponent } from './view-all-purchased-line-item-sales-record.component';

describe('ViewAllPurchasedLineItemSalesRecordComponent', () => {
  let component: ViewAllPurchasedLineItemSalesRecordComponent;
  let fixture: ComponentFixture<ViewAllPurchasedLineItemSalesRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllPurchasedLineItemSalesRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllPurchasedLineItemSalesRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
