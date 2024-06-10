import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrevisionStockFormComponent } from './prevision-stock-form.component';

describe('PrevisionStockFormComponent', () => {
  let component: PrevisionStockFormComponent;
  let fixture: ComponentFixture<PrevisionStockFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrevisionStockFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrevisionStockFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
