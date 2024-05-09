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

@Component({
  selector: 'app-entre-sortie',
  templateUrl: './entre-sortie.component.html',
  styleUrl: './entre-sortie.component.scss'
})
export class EntreSortieComponent implements OnInit{
  es: EntreSortie = new EntreSortie();
  ess: EntreSortie[] = [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private esService: ESService, private bacService : BacService, private router: Router, private _dialog: MatDialog) {
  }
  displayedColumns: string[] = [
    'id',
    'quantite',
    'date',
    'typeES',
    'idBac'
  ];
  ngOnInit() {
    this.getes();
  }

  getes(){
    this.esService.getEs().subscribe({
      next: (ess)=> {
        ess.forEach((es: EntreSortie) => {
          this.bacService.getBacById(es.idBac).subscribe({
            next: (bac) => {
              es.bac = bac;
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
}
