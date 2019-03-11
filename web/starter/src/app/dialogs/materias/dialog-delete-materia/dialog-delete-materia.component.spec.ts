import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeleteMateriaComponent } from './dialog-delete-materia.component';

describe('DialogDeleteMateriaComponent', () => {
  let component: DialogDeleteMateriaComponent;
  let fixture: ComponentFixture<DialogDeleteMateriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogDeleteMateriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeleteMateriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
