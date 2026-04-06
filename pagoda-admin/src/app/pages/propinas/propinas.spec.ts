import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Propinas } from './propinas';

describe('Propinas', () => {
  let component: Propinas;
  let fixture: ComponentFixture<Propinas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Propinas],
    }).compileComponents();

    fixture = TestBed.createComponent(Propinas);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
