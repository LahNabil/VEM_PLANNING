import {Component, OnInit, ViewChild} from '@angular/core';
import {Bac} from "../../models/Bac";
import {ProductService} from "../../Services/product.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {BacService} from "../../Services/bac.service";
import {EntreSortie} from "../../models/EntreSortie";
import {AddProductComponent} from "../add-product/add-product.component";
import {BacStockComponent} from "../bac-stock/bac-stock.component";
import {AddBacComponent} from "../add-bac/add-bac.component";
import * as XLSX from "xlsx";

@Component({
  selector: 'app-bac',
  templateUrl: './bac.component.html',
  styleUrl: './bac.component.scss'
})
export class BacComponent implements OnInit{

  bacs: Bac = new Bac();
  bac: Bac[] = [];
  creux!: number;
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = [
    'idBac',
    'idDepot',
    'capacity',
    'totalImpom',
    'status',
    'dateOuverture',
    'capacityUsed',
    'idProduct',
    'productName',
    'creux',
    'actions'


  ];

  constructor(private productService: ProductService, private bacService : BacService, private router: Router, private _dialog: MatDialog) {
  }
  ngOnInit() {
    this.getBacs();
  }

  getBacs(){
    this.bacService.getBac().subscribe({
      next: (bacs)=>{
        bacs.forEach((bac : Bac) =>{
          this.productService.getProductById(bac.idProduct).subscribe({
            next: (product)=>{
              bac.product = product;
            }
          });
          this.bacService.calculerCreux(bac.idBac).subscribe({
            next: (creux) =>{
              bac.creux = creux;
            },
            error: (error) => {
              console.log("Error calculating creux: ", error);
            }

          });
        });
        this.dataSource = new MatTableDataSource(bacs);
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
  deleteBac(idBac: String|undefined){
    const isConfirmed = window.confirm("Êtes-vous sûr de vouloir supprimer ce Bac ?");
    if (isConfirmed) {
      this.bacService.deleteBac(idBac).subscribe(data => {
        window.location.reload();
      });
    }
  }


  openStockForm(data:any) {
    this._dialog.open(BacStockComponent,{
      data,
    });
  }

  deleteProduct(idProduit: any) {

  }

  openEditForm() {

  }

  openAddForm() {
    this._dialog.open(AddBacComponent);
  }
  fileName = "BacExcelSheet.xlsx";
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
