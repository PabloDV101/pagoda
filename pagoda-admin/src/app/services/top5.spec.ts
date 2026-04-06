import { TestBed } from '@angular/core/testing';

import { Top5 } from './top5';

describe('Top5', () => {
  let service: Top5;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Top5);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
