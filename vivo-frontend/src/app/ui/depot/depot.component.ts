import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DepotService} from "../../Services/depot.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Depot} from "../../models/Depot";
import {AddProductComponent} from "../add-product/add-product.component";
import {ProductService} from "../../Services/product.service";
import {DepotDetailsComponent} from "../depot-details/depot-details.component";
import {AddDepotComponent} from "../add-depot/add-depot.component";
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-depot',
  templateUrl: './depot.component.html',
  styleUrl: './depot.component.scss'
})
export class DepotComponent implements OnInit {


  depot: Depot = new Depot();
  depots: Depot[] = [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = [
    'idDepot',
    'nameDepot',
    'zone',
    'area',
    'bacDtos',
    'product',
    'stock',
    'actions'
    // 'BacDto'


  ];

  constructor(private productService: ProductService, private depotService: DepotService, private router: Router, private _dialog: MatDialog) {
  }

  ngOnInit() {
    this.getAllDepots();
  }

  getAllDepots() {
    this.depotService.getDepots().subscribe({
      next: (res) => {
        res.forEach((depot: Depot) => {
          this.depotService.calculerStock(depot.idDepot).subscribe({
            next: (stock) => {
              depot.stock = stock;
              console.log(res);
            }
          });
          depot.bacDtos?.forEach(bac => {
            this.productService.getProductById(bac.idProduct).subscribe({
              next: (product) => {
                bac.product = product;
              }
            })
          })
        });
        this.dataSource = new MatTableDataSource(res);
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


  // openDetailForm(data: any){
  //   this._dialog.open(DepotDetailsComponent,{
  //     data,
  //   });
  // }
  DepotDetail(idDepot: number | undefined) {
    if (idDepot !== undefined) {
      this.router.navigate(['depot-detail', idDepot])
    } else {
      console.error("Id incorrect")
    }

  }

  deleteDepot(idDepot: any) {

  }
  openEditForm(data: any){
    this._dialog.open(AddDepotComponent,{
      data,
    });
  }

  openAddForm() {
    this._dialog.open(AddDepotComponent);

  }
/** default name for the downloaded file **/
fileName = "DepotExcelSheet.xlsx";
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


