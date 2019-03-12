import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import {Partido} from '../../../interfaces/partido-response';
import {PartidoCreateDto} from '../../../dto/partido-create-dto';
import {PartidoService} from '../../../services/partido.service';
@Component({
  selector: 'app-dialog-new-partido',
  templateUrl: './dialog-new-partido.component.html',
  styleUrls: ['./dialog-new-partido.component.scss']
})
export class DialogNewPartidoComponent implements OnInit {
  allPartidos: Partido[];
  form: FormGroup;

  constructor(private fb: FormBuilder, private partidoService: PartidoService,
    public dialogRef: MatDialogRef<DialogNewPartidoComponent>) { }

  ngOnInit() {
    this.getPartidos();
    this.createForm();
  }
  createForm() {
    this.form = this.fb.group({
      nombre: [null, Validators.compose([Validators.required])],
      siglas: [null, Validators.compose([Validators.required])],
      picture:[null, Validators.compose([Validators.required])]
    });
  }
  addPartido() {
    const partidoCreateDto: PartidoCreateDto = <PartidoCreateDto>this.form.value;
    this.partidoService.createPartido(partidoCreateDto).subscribe(
      partido => {
        this.dialogRef.close();
      }
    );
  }

  getPartidos() {
    this.partidoService.getAllPartidos().subscribe(r => this.allPartidos = r.rows);
  }
}
