import {Component, OnInit, ViewChild} from '@angular/core';
import {Product} from "../../models/Product";
import {ProductService} from "../../Services/product.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AddProductComponent} from "../add-product/add-product.component";
import {Prevision} from "../../models/Prevision";
import {PrevisionService} from "../../Services/prevision.service";
import {BacItem} from "../../models/BacItem";
import {AddPrevisionComponent} from "../add-prevision/add-prevision.component";
import {PrevisionStockFormComponent} from "../prevision-stock-form/prevision-stock-form.component";
import * as XLSX from "xlsx";

@Component({
  selector: 'app-prevision',
  templateUrl: './prevision.component.html',
  styleUrl: './prevision.component.scss'
})
export class PrevisionComponent implements OnInit{
  prevision: Prevision = new Prevision();
  previsions: Prevision[] = [];
  constructor(private previsionService : PrevisionService, private router: Router, private _dialog: MatDialog) {
  }
  displayedColumns: string[] = [
    'idPrevision',
    'business',
    'supplyEnveloppe',
    'nameProduct',
    'date',
    'foreCaste',
    'vReel',
    'ABS',
    'accuracy',
    'actions'
  ];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  ngOnInit(){
    this.getPrevisions();
  }
  openAddForm(){
    this._dialog.open(AddPrevisionComponent);
  }
  openEditForm(data: any){
    this._dialog.open(AddPrevisionComponent,{
      data,
    });
  }
  openStockForm(data: any){
    this._dialog.open(PrevisionStockFormComponent,{
      data,
    });
  }
  deletePrevision(idPrevision: number|undefined){
    const isConfirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette prevision ?");
    if (isConfirmed) {
      this.previsionService.deletePrevisionById(idPrevision).subscribe(data => {
        window.location.reload();
      });
    }
  }

  getPrevisions() {
    this.previsionService.getPrevision().subscribe({
      next: (previsions) => {
        // Mise à jour des prévisions avec vReel
        previsions.forEach((prevision: Prevision) => {
          this.previsionService.calculerVreel(prevision.idPrevision).subscribe({
            next: (vReel) => {
              prevision.vReel = vReel;

              // Après avoir mis à jour vReel, mise à jour de ABS
              this.previsionService.calculerABS(prevision.idPrevision).subscribe({
                next: (ABS) => {
                  prevision.ABS = ABS;

                  this.previsionService.calculerAccuracy(prevision.idPrevision).subscribe({
                    next:(accuracy)=>{
                      prevision.accuracy = accuracy;
                  }
                  })


                  // Mise à jour du dataSource uniquement après avoir mis à jour vReel et ABS
                  this.dataSource = new MatTableDataSource(previsions);
                  this.dataSource.sort = this.sort;
                  this.dataSource.paginator = this.paginator;
                }
              });
            }
          });
        });
      }
    });
  }

  // deleteProduct(idProduit: number|undefined){
  //   const isConfirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette assurance ?");
  //   if (isConfirmed) {
  //     this.productService.deleteProduct(idProduit).subscribe(data => {
  //       window.location.reload();
  //     });
  //   }
  // }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  fileName = "PrevisionsExcelSheet.xlsx";
  exportExcel(){
    let data = document.getElementById("table-data");
    if (data) {
      // Clone the table
      const clonedTable = data.cloneNode(true) as HTMLElement;
      const columnIdToRemove = "table-action";

      // Remove the "Action" column from the cloned table
      this.removeColumnById(clonedTable, columnIdToRemove);

      const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(clonedTable);
      const wb: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
      XLSX.writeFile(wb, this.fileName);
    }

  }
  /** This method removes the column with the specified ID from the cloned table. **/
  removeColumnById(table: HTMLElement, columnId: string): void {
    const columnIndex = this.getColumnIndexById(table, columnId);
    if (columnIndex !== -1) {
      const rows = table.querySelectorAll('tr');
      rows.forEach(row => {
        const cells = row.querySelectorAll('th, td');
        if (cells[columnIndex]) {
          cells[columnIndex].remove();
        }
      });
    }
  }
  /** This method returns the index of the column with the specified ID. This index is used to identify which column to remove.**/
  getColumnIndexById(table: HTMLElement, columnId: string): number {
    const thElements = table.querySelectorAll('thead th');
    for (let i = 0; i < thElements.length; i++) {
      if (thElements[i].id === columnId) {
        return i;
      }
    }
    return -1;
  }


}


