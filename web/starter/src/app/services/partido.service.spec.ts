import { TestBed, inject } from '@angular/core/testing';

import { PartidoService } from './partido.service';

describe('PartidoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PartidoService]
    });
  });

  it('should be created', inject([PartidoService], (service: PartidoService) => {
    expect(service).toBeTruthy();
  }));
});
