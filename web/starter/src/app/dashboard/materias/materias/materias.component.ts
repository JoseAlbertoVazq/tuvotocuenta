import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { Materia } from '../../../interfaces/materias-response'
import { MateriaContainer } from '../../../interfaces/materia-container';
import {MateriaService} from '../../../services/materia.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['./materias.component.scss']
})
export class MateriasComponent implements OnInit {
  displayedColumns: string[] = ['name', 'actions'];
  dataSource;
  materiasList: Materia[];
  materias: MateriaContainer;

  constructor(private materiaService: MateriaService, public snackBar: MatSnackBar,
    public dialog: MatDialog, private authService: AuthService, private titleService: Title) { }
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit() {
    this.titleService.setTitle('Materias');
    this.getListMaterias('List of Materias loaded');
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getListMaterias(mensaje: string) {
    const totalSum = 0;
    this.materiaService.getAllMaterias().toPromise()
      .then(receivedMaterias => {
        // receivedUsers.rows.forEach(badges => {totalSum+=badge.points})
        this.dataSource = new MatTableDataSource(receivedMaterias.rows);
        this.dataSource.paginator = this.paginator;
      })
      .catch(() => this.snackBar.open('Hubo un error mientras cargábamos las materias', 'Close', { duration: 3000 }));
  }
}
