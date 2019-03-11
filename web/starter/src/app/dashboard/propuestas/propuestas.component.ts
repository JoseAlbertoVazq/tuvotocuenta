import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { AuthService } from 'src/app/services/auth.service';
import { Propuesta } from '../../interfaces/propuesta-response';
import { PropuestaContainer } from '../../interfaces/propuesta-container';
import { PropuestaService } from '../../services/propuesta.service';
import { DialogNewPropuestaComponent} from 'src/app/dialogs/propuestas/dialog-new-propuesta/dialog-new-propuesta.component';
import { DialogEditPropuestaComponent } from 'src/app/dialogs/propuestas/dialog-edit-propuesta/dialog-edit-propuesta.component';
import { DialogDeletePropuestaComponent } from 'src/app/dialogs/propuestas/dialog-delete-propuesta/dialog-delete-propuesta.component';

@Component({
  selector: 'app-propuestas',
  templateUrl: './propuestas.component.html',
  styleUrls: ['./propuestas.component.scss']
})
export class PropuestasComponent implements OnInit {
  displayedColumns: string[] = ['titulo', 'partido', 'materia', 'actions'];
  dataSource;
  propuestaList: Propuesta[];
  propuesta: PropuestaContainer;
  constructor(private propuestaService: PropuestaService, public snackBar: MatSnackBar,
    public dialog: MatDialog, private authService: AuthService, private titleService: Title) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.titleService.setTitle('Propuesta');
    this.getListPropuesta('List of Propuesta loaded');
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getListPropuesta(mensaje: string) {
    const totalSum = 0;
    this.propuestaService.getAllPropuestas().toPromise()
      .then(receivedPropuesta => {
        this.dataSource = new MatTableDataSource(receivedPropuesta.rows);
        this.dataSource.paginator = this.paginator;
      })
      .catch(() => this.snackBar.open('Hubo un error mientras cargábamos las propuestas', 'Close', { duration: 3000 }));
  }

  openDialogNewPropuesta() {
    const dialogoNuevaPropuesta = this.dialog.open(DialogNewPropuestaComponent);

    dialogoNuevaPropuesta.afterClosed().subscribe(result => {
      this.getListPropuesta('Propuesta creada');
    });

  }
  openDialogEditPropuesta(element: Propuesta) {
    const dialogoEditPropuesta = this.dialog.open(DialogEditPropuestaComponent, {
      data: { propuesta: element }
    });

    dialogoEditPropuesta.afterClosed().subscribe(result => {
      this.getListPropuesta('Propuesta editada');
    });
  }

  openDialogDeletePropuesta(element: Propuesta) {
    const dialogoDeletePropuesta = this.dialog.open(DialogDeletePropuestaComponent, {
      data: { id: element.id }
    });

    dialogoDeletePropuesta.afterClosed().subscribe(result => {
      this.getListPropuesta('Propuesta eliminada');
    });
  }
}
