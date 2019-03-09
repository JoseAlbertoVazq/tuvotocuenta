import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatPaginator, MatSnackBar, MatTableDataSource } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { UsuariosResponse } from 'src/app/interfaces/usuarios-response';
import { UsuarioService } from 'src/app/services/usuario.service';
import { UsuarioResponse } from '../../interfaces/usuario-response';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {
  users: UsuariosResponse;
  userList: UsuarioResponse[];
  displayedColumns: string[] = ['picture', 'name', 'email', 'ciudad', 'actions'];
  dataSource;
  roles: string[];
  constructor(private snackBar: MatSnackBar, private router: Router,
    public dialog: MatDialog, private userService: UsuarioService, private titleService: Title) { }
  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit() {
  }

  getAll() {
    const totalSum = 0;
    this.userService.getAllUsuarios().toPromise()
      .then(receivedUsers => {
        this.dataSource = new MatTableDataSource(receivedUsers.rows);
        this.dataSource.paginator = this.paginator;
      })
      .catch(() => this.snackBar.open('There was an error when we were loading data.', 'Close', { duration: 3000 }));
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  // openDialogDeleteUser(u: UserResponse) {
  //   const dialogDeleteUser = this.dialog.open(DialogDeleteUserComponent, { data: { user: u } });
  //   dialogDeleteUser.afterClosed().subscribe(result => {
  //     this.getAll();
  //   });
  // }
  // //dialog to create users
  // openDialogNewUser() {
  //   const dialogNewUser = this.dialog.open(DialogCreateUserComponent, { width: '500px' });
  //   dialogNewUser.afterClosed().subscribe(result => {
  //     this.getAll();
  //   });
  // }
  // //dialog to update users
  // openDialogUpdateUser(userResponse: UserResponse) {
  //   const dialogUpdateUser = this.dialog.open(DialogEditUserComponent, { width: '500px', data: { user: userResponse } });
  //   dialogUpdateUser.afterClosed().subscribe(result => {
  //     this.getAll();
  //   });
  // }
}
