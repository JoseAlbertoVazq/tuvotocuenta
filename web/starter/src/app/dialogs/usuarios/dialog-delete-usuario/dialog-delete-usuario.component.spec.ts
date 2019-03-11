import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeleteUsuarioComponent } from './dialog-delete-usuario.component';

describe('DialogDeleteUsuarioComponent', () => {
  let component: DialogDeleteUsuarioComponent;
  let fixture: ComponentFixture<DialogDeleteUsuarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogDeleteUsuarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeleteUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
