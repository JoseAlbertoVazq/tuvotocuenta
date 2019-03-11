import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogNewPartidoComponent } from './dialog-new-partido.component';

describe('DialogNewPartidoComponent', () => {
  let component: DialogNewPartidoComponent;
  let fixture: ComponentFixture<DialogNewPartidoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogNewPartidoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogNewPartidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
