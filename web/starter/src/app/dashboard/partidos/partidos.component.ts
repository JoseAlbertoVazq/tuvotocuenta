import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { AuthService } from 'src/app/services/auth.service';
import {Partido} from '../../interfaces/partido-response';
import {PartidoContainer} from '../../interfaces/partido-container';
import {PartidoService} from '../../services/partido.service';
import {DialogNewPartidoComponent} from 'src/app/dialogs/partidos/dialog-new-partido/dialog-new-partido.component';
import {DialogEditPartidoComponent} from 'src/app/dialogs/partidos/dialog-edit-partido/dialog-edit-partido.component';
import {DialogDeletePartidoComponent} from 'src/app/dialogs/partidos/dialog-delete-partido/dialog-delete-partido.component';

@Component({
  selector: 'app-partidos',
  templateUrl: './partidos.component.html',
  styleUrls: ['./partidos.component.scss']
})
export class PartidosComponent implements OnInit {
  displayedColumns: string[] = ['picture','siglas', 'name', 'actions'];
  dataSource;
  partidosList: Partido[];
  partidos: PartidoContainer;

  constructor(private partidoService: PartidoService, public snackBar: MatSnackBar,
    public dialog: MatDialog, private authService: AuthService, private titleService: Title) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.titleService.setTitle('Partidos');
    this.getListPartidos('List of Partidos loaded');
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getListPartidos(mensaje: string) {
    const totalSum = 0;
    this.partidoService.getAllPartidos().toPromise()
      .then(receivedPartidos => {
        this.dataSource = new MatTableDataSource(receivedPartidos.rows);
        this.dataSource.paginator = this.paginator;
      })
      .catch(() => this.snackBar.open('Hubo un error mientras cargábamos los partidos', 'Close', { duration: 3000 }));
  }

  openDialogNewPartido() {
    const dialogoNuevoPartido = this.dialog.open(DialogNewPartidoComponent);

    dialogoNuevoPartido.afterClosed().subscribe(result => {
      this.getListPartidos('Partido creado');
    });

  }
  openDialogEditPartido(element: Partido) {
    const dialogoEditPartido = this.dialog.open(DialogEditPartidoComponent, {
      data: { partido: element }
    });

    dialogoEditPartido.afterClosed().subscribe(result => {
      this.getListPartidos('Partido editado');
    });
  }

  openDialogDeletePartido(element: Partido) {
    const dialogoDeletePartido = this.dialog.open(DialogDeletePartidoComponent, {
      data: { id: element.id }
    });

    dialogoDeletePartido.afterClosed().subscribe(result => {
      this.getListPartidos('Partido eliminado');
    });
  }
}
