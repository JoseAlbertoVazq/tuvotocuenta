import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { UsuarioService } from 'src/app/services/usuario.service';


@Component({
  selector: 'app-dialog-delete-usuario',
  templateUrl: './dialog-delete-usuario.component.html',
  styleUrls: ['./dialog-delete-usuario.component.scss']
})
export class DialogDeleteUsuarioComponent implements OnInit {

  checkedRobot: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
    private userService: UsuarioService, public dialogRef: MatDialogRef<DialogDeleteUsuarioComponent>,
    public snackBar: MatSnackBar) { }

  ngOnInit() {

  }
  //check if its a robot with captcha
  captcha() {
    if (this.checkedRobot) {
      return true;
    } else {
      return false;
    }
  }
  //delete user
  delete() {
    this.userService.deleteUsuario(this.data.user.id).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('Hubo un error al intentar borrar este usuario', 'Close', { duration: 3000 }));
  }
}
