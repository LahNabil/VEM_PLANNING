import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntreSortieComponent } from './entre-sortie.component';

describe('EntreSortieComponent', () => {
  let component: EntreSortieComponent;
  let fixture: ComponentFixture<EntreSortieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EntreSortieComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EntreSortieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
