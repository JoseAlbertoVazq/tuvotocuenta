import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Materia } from '../../../interfaces/materias-response'
import { MateriaCreateDto } from '../../../dto/materia-create-dto';
import {MateriaService} from '../../../services/materia.service';

@Component({
  selector: 'app-dialog-new-materia',
  templateUrl: './dialog-new-materia.component.html',
  styleUrls: ['./dialog-new-materia.component.scss']
})
export class DialogNewMateriaComponent implements OnInit {
  allMaterias: Materia[];
  form: FormGroup;
  constructor(private fb: FormBuilder, private materiaService: MateriaService,
    public dialogRef: MatDialogRef<DialogNewMateriaComponent>) { }

  ngOnInit() {
    this.getMaterias();
    this.createForm();
  }
  createForm() {
    this.form = this.fb.group({
      nombre: [null, Validators.compose([Validators.required])]
    });
  }
  addMateria() {
    const materiaCreateDto: MateriaCreateDto = <MateriaCreateDto>this.form.value;
    this.materiaService.createMateria(materiaCreateDto).subscribe(
      materia => {
        this.dialogRef.close();
      }
    );
  }

  getMaterias() {
    this.materiaService.getAllMaterias().subscribe(r => this.allMaterias = r.rows);
  }
}
