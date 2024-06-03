import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBacComponent } from './edit-bac.component';

describe('EditBacComponent', () => {
  let component: EditBacComponent;
  let fixture: ComponentFixture<EditBacComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditBacComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditBacComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
