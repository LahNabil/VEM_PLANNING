import {Component, OnInit, ViewChild} from '@angular/core';
import {EntreSortie} from "../../models/EntreSortie";
import {Bac} from "../../models/Bac";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {StockEsDto} from "../../models/StockEsDto";
import {ProductService} from "../../Services/product.service";
import {ESService} from "../../Services/es.service";
import {BacService} from "../../Services/bac.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-montly-report',
  templateUrl: './montly-report.component.html',
  styleUrl: './montly-report.component.scss'
})
export class MontlyReportComponent implements OnInit{
  stockEsDto: StockEsDto = new StockEsDto();
  stockEsDtos: StockEsDto[] = [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private esService: ESService,private router: Router, private _dialog: MatDialog) {
  }
  displayedColumns: string[] = [
    'dateJour',
    'stockInitial',
    'entre',
    'sortie',
    'stockFinale'
  ];
  ngOnInit() {
  }


}
