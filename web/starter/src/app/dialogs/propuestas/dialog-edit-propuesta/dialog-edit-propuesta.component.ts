import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Propuesta } from '../../../interfaces/propuesta-response';
import { PropuestaContainer } from '../../../interfaces/propuesta-container';
import { PropuestaService } from '../../../services/propuesta.service';
import { PartidoService } from 'src/app/services/partido.service';
import { MateriaService } from 'src/app/services/materia.service';
import { PropuestaCreateDto } from 'src/app/dto/propuesta-create-dto';
import { Partido } from 'src/app/interfaces/partido-response';
import { Materia } from 'src/app/interfaces/materias-response';


@Component({
  selector: 'app-dialog-edit-propuesta',
  templateUrl: './dialog-edit-propuesta.component.html',
  styleUrls: ['./dialog-edit-propuesta.component.scss']
})
export class DialogEditPropuestaComponent implements OnInit {
  public form: FormGroup;
  allPropuestas: Propuesta[];
  propuesta: Propuesta;
  materiaSelected;
  partidoSelected;
  allPartidos: Partido[];
  allMaterias: Materia[];
  constructor(private fb: FormBuilder, private partidoService: PartidoService, private propuestaService: PropuestaService,
    private materiaService: MateriaService,
    public dialogRef: MatDialogRef<DialogEditPropuestaComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

   ngOnInit() {
     this.propuesta = this.data.propuesta;
     this.getAllMaterias();
     this.getAllPartidos();
     this.form = this.fb.group({
      titulo: [this.data.propuesta.titulo, Validators.compose([Validators.required])],
      contenido: [this.data.propuesta.contenido, Validators.compose([Validators.required])],
      partido: [this.data.propuesta.partido.nombre, Validators.compose([Validators.required])],
      materia: [this.data.propuesta.materia.nombre, Validators.compose([Validators.required])]
    });
   }

   editPropuesta() {
     const propuestaCreateDto: PropuestaCreateDto = <PropuestaCreateDto>this.form.value;
     this.propuestaService.updatePropuesta(this.propuesta.id, propuestaCreateDto).subscribe(
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
