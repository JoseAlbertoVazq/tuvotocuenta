import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Propuesta } from '../../../interfaces/propuesta-response';
import { PropuestaContainer } from '../../../interfaces/propuesta-container';
import { PropuestaService } from '../../../services/propuesta.service';
import { PartidoService } from 'src/app/services/partido.service';
import { MateriaService } from 'src/app/services/materia.service';
import { PropuestaCreateDto } from 'src/app/dto/propuesta-create-dto';
import { Partido } from 'src/app/interfaces/partido-response';
import { Materia } from 'src/app/interfaces/materias-response';

@Component({
  selector: 'app-dialog-new-propuesta',
  templateUrl: './dialog-new-propuesta.component.html',
  styleUrls: ['./dialog-new-propuesta.component.scss']
})
export class DialogNewPropuestaComponent implements OnInit {
  form: FormGroup;
  allPropuestas: Propuesta[];
  materiaSelected;
  partidoSelected;
  allPartidos: Partido[];
  allMaterias: Materia[];

  constructor(private fb: FormBuilder, private partidoService: PartidoService, private propuestaService: PropuestaService,
   private materiaService: MateriaService, public dialogRef: MatDialogRef<DialogNewPropuestaComponent>) { }

  ngOnInit() {
    this.getAllMaterias();
    this.getAllPartidos();
    this.createForm();
  }
  createForm() {
    this.form = this.fb.group({
      titulo: [null, Validators.compose([Validators.required])],
      contenido: [null, Validators.compose([Validators.required])],
      partido: [null, Validators.compose([Validators.required])],
      materia: [null, Validators.compose([Validators.required])]
    });
  }
  addPropuesta() {
    const propuestaCreateDto: PropuestaCreateDto = <PropuestaCreateDto>this.form.value;
    this.propuestaService.createPropuesta(propuestaCreateDto).subscribe(
      propuesta => {
        this.dialogRef.close();
      }
    );
  }

  getPropuestas() {
    this.propuestaService.getAllPropuestas().subscribe(r => this.allPropuestas = r.rows);
  }

  getAllPartidos() {
    this.partidoService.getAllPartidos().subscribe(r => this.allPartidos = r.rows);
  }

  getAllMaterias() {
    this.materiaService.getAllMaterias().subscribe(r => this.allMaterias = r.rows);
  }
}
