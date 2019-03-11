import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {MateriaService} from '../../../services/materia.service';

@Component({
  selector: 'app-dialog-delete-materia',
  templateUrl: './dialog-delete-materia.component.html',
  styleUrls: ['./dialog-delete-materia.component.scss']
})
export class DialogDeleteMateriaComponent implements OnInit {
  checkedRobot: boolean;
  constructor(private materiaService: MateriaService, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogDeleteMateriaComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }
  captcha() {
    if (this.checkedRobot) {
      return true;
    } else {
      return false;
    }
  }
  delete() {
    this.materiaService.deleteMateria(this.data.id).subscribe(resp => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('Hubo un error al intentar borrar esta materia', 'Close', {duration: 3000}));
  }
}
