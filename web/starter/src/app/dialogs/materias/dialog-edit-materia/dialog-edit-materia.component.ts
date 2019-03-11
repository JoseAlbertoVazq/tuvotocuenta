import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Materia } from '../../../interfaces/materias-response'
import { MateriaCreateDto } from '../../../dto/materia-create-dto';
import {MateriaService} from '../../../services/materia.service';

@Component({
  selector: 'app-dialog-edit-materia',
  templateUrl: './dialog-edit-materia.component.html',
  styleUrls: ['./dialog-edit-materia.component.scss']
})
export class DialogEditMateriaComponent implements OnInit {
  public form: FormGroup;
  materia: Materia;
  materias: Materia[];

  constructor(private fb: FormBuilder, private materiaService: MateriaService,
    public dialogRef: MatDialogRef<DialogEditMateriaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.materia = this.data.materia;
    this.getMaterias();
    this.form = this.fb.group({
      nombre: [this.data.materia.nombre, Validators.compose([Validators.required])]
    });
  }

  editMateria() {
    const materiaEditDto: MateriaCreateDto = <MateriaCreateDto>this.form.value;
    this.materiaService.updateMateria(this.materia.id, materiaEditDto).subscribe(result => {
      this.dialogRef.close();
    });
  }

  getMaterias() {
    this.materiaService.getAllMaterias().subscribe(result => {
      this.materias = result.rows;
    });
  }

}
