import { TestBed } from '@angular/core/testing';

import { Propinas } from './propinas';

describe('Propinas', () => {
  let service: Propinas;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Propinas);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
