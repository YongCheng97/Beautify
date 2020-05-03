import { TestBed } from '@angular/core/testing';

import { SalesForUsService } from './sales-for-us.service';

describe('SalesForUsService', () => {
  let service: SalesForUsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesForUsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
