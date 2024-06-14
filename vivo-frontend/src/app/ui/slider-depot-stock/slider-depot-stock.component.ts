import {Component, OnInit, ViewChild} from '@angular/core';
import {Depot} from "../../models/Depot";
import {StockProduitDto} from "../../models/StockProduitDto";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {HistoryService} from "../../Services/history.service";
import {DepotService} from "../../Services/depot.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-slider-depot-stock',
  templateUrl: './slider-depot-stock.component.html',
  styleUrl: './slider-depot-stock.component.scss'
})
export class SliderDepotStockComponent implements OnInit{
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
  selectedIndex =0;
  showPrev(i: number) {
    if (i > 0) {
      this.selectedIndex = i - 1;
    } else {
      this.selectedIndex = this.depots.length - 1;
    }
  }

  showNext(i: number) {
    if (i < this.depots.length - 1) {
      this.selectedIndex = i + 1;
    } else {
      this.selectedIndex = 0;
    }
  }
}
