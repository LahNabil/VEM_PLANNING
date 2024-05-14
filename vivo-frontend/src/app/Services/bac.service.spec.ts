import { TestBed } from '@angular/core/testing';

import { BacService } from './bac.service';

describe('BacService', () => {
  let service: BacService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BacService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
