import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEditMateriaComponent } from './dialog-edit-materia.component';

describe('DialogEditMateriaComponent', () => {
  let component: DialogEditMateriaComponent;
  let fixture: ComponentFixture<DialogEditMateriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogEditMateriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEditMateriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
