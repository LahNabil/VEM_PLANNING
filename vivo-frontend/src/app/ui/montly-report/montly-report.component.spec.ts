import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MontlyReportComponent } from './montly-report.component';

describe('MontlyReportComponent', () => {
  let component: MontlyReportComponent;
  let fixture: ComponentFixture<MontlyReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MontlyReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MontlyReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
