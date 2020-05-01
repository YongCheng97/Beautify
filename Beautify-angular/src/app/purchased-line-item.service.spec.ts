import { TestBed } from '@angular/core/testing';

import { PurchasedLineItemService } from './purchased-line-item.service';

describe('PurchasedLineItemService', () => {
  let service: PurchasedLineItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PurchasedLineItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
