import {Component, OnInit, ViewChild} from '@angular/core';
import {DepotService} from "../../Services/depot.service";
import {Depot} from "../../models/Depot";
import {StockProduitDto} from "../../models/StockProduitDto";
import {HistoryService} from "../../Services/history.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import * as XLSX from "xlsx";

@Component({
  selector: 'app-stock-depot',
  templateUrl: './stock-depot.component.html',
  styleUrl: './stock-depot.component.scss'
})
export class StockDepotComponent implements OnInit{
  depots : Depot[] = [];
  depot : Depot = new Depot();
  stocks: { [key: string]: StockProduitDto[] } = {};
  histories: History[] = [];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    'idHistory',
    'dateJour',
    'nameProduct',
    'stock',
    'idDepot'


  ];

  constructor(private historyService: HistoryService,private depotService:DepotService, private router: Router,private _dialog: MatDialog) {
  }
  ngOnInit() {
    this.getDepots();
    this.getHistories();
  }
  getHistories() {
    this.historyService.getHistories().subscribe({
      next: (res) => {
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
  getDepots(): void {
    this.depotService.getDepots().subscribe(
      depots => {
        this.depots = depots;
        this.depots.forEach(depot => {
          this.getStockForDepot((depot.idDepot).toString());
        });
      },
      error => {
        console.error('Error fetching depots:', error);
      }
    );
  }
  getStockForDepot(idDepot: string){
    this.depotService.calculerStocksProduits(idDepot).subscribe(
      stocks=>{
        this.stocks[idDepot] = stocks;
      },
      error => {
        console.error(`Error fetching stocks for depot ${idDepot}:`, error);
      }
    )
  }
  fileName = "StockExcelSheet.xlsx";
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
