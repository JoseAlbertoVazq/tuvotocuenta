import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {Partido} from '../../../interfaces/partido-response';
import {PartidoCreateDto} from '../../../dto/partido-create-dto';
import {PartidoService} from '../../../services/partido.service';

@Component({
  selector: 'app-dialog-edit-partido',
  templateUrl: './dialog-edit-partido.component.html',
  styleUrls: ['./dialog-edit-partido.component.scss']
})
export class DialogEditPartidoComponent implements OnInit {
  public form: FormGroup;
  partido: Partido;
  partidos: Partido[];

  constructor(private fb: FormBuilder, private partidoService: PartidoService,
    public dialogRef: MatDialogRef<DialogEditPartidoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.partido = this.data.partido;
    this.getPartidos();
    this.form = this.fb.group({
      nombre: [this.data.partido.nombre, Validators.compose([Validators.required])],
      siglas: [this.data.partido.siglas, Validators.compose([Validators.required])],
      picture: [this.data.partido.picture, Validators.compose([Validators.required])]
    });
  }

  editPartido() {
    const partidoEditDto: PartidoCreateDto = <PartidoCreateDto>this.form.value;
    this.partidoService.updatePartido(this.partido.id, partidoEditDto).subscribe(result => {
      this.dialogRef.close();
    });
  }

  getPartidos() {
    this.partidoService.getAllPartidos().subscribe(result => {
      this.partidos = result.rows;
    });
  }
}
