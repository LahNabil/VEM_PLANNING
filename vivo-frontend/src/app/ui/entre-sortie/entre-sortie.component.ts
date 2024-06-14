import {Component, OnInit, ViewChild} from '@angular/core';
import {EntreSortie} from "../../models/EntreSortie";
import {Bac} from "../../models/Bac";
import {MatTableDataSource} from "@angular/material/table";
import {ProductService} from "../../Services/product.service";
import {BacService} from "../../Services/bac.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {ESService} from "../../Services/es.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import * as XLSX from "xlsx";

@Component({
  selector: 'app-entre-sortie',
  templateUrl: './entre-sortie.component.html',
  styleUrl: './entre-sortie.component.scss'
})
export class EntreSortieComponent implements OnInit{
  es: EntreSortie = new EntreSortie();
  ess: EntreSortie[] = [];
  bac: Bac = new Bac();
  bacs: Bac[]= [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private productService : ProductService,private esService: ESService, private bacService : BacService, private router: Router, private _dialog: MatDialog) {
  }
  displayedColumns: string[] = [
    'id',
    'quantite',
    'date',
    'typeES',
    'business',
    'idBac',
    'nameProduct'
  ];
  ngOnInit() {
    this.getes();
    this.getBacs();

  }


  getBacs(){
    this.bacService.getBac().subscribe(data=>{
      this.bacs = data;
    })
  }


  getes(){
    this.esService.getEs().subscribe({
      next: (ess)=> {
        ess.forEach((es: EntreSortie) => {
          this.bacService.getBacById(es.idBac).subscribe({
            next: (bac) => {
              es.bac = bac;
              console.log(es);
            }
          });
        });
        this.dataSource = new MatTableDataSource(ess);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  fileName = "EntreeSortieExcelSheet.xlsx";
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


