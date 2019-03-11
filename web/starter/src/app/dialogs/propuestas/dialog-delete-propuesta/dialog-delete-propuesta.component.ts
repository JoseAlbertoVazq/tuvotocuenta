import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PropuestaService } from '../../../services/propuesta.service';

@Component({
  selector: 'app-dialog-delete-propuesta',
  templateUrl: './dialog-delete-propuesta.component.html',
  styleUrls: ['./dialog-delete-propuesta.component.scss']
})
export class DialogDeletePropuestaComponent implements OnInit {
  checkedRobot: boolean;
  constructor(private propuestaService: PropuestaService, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogDeletePropuestaComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }


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
    this.propuestaService.deletePropuesta(this.data.id).subscribe(resp => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('Hubo un error al intentar borrar esta propuesta', 'Close', {duration: 3000}));
  }

}
