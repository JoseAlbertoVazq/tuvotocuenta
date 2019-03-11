import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogNewMateriaComponent } from './dialog-new-materia.component';

describe('DialogNewMateriaComponent', () => {
  let component: DialogNewMateriaComponent;
  let fixture: ComponentFixture<DialogNewMateriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogNewMateriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogNewMateriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
