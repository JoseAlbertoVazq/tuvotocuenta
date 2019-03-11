import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {PartidoService} from '../../../services/partido.service';
@Component({
  selector: 'app-dialog-delete-partido',
  templateUrl: './dialog-delete-partido.component.html',
  styleUrls: ['./dialog-delete-partido.component.scss']
})
export class DialogDeletePartidoComponent implements OnInit {
  checkedRobot: boolean;
  constructor(private partidoService: PartidoService, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogDeletePartidoComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }


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
    this.partidoService.deletePartido(this.data.id).subscribe(resp => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('Hubo un error al intentar borrar este partido', 'Close', {duration: 3000}));
  }
}
