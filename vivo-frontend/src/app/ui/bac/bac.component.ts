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

@Component({
  selector: 'app-bac',
  templateUrl: './bac.component.html',
  styleUrl: './bac.component.scss'
})
export class BacComponent implements OnInit{

  bacs: Bac = new Bac();
  bac: Bac[] = [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = [
    'idBac',
    'capacity',
    'totalImpom',
    'status',
    'dateOuverture',
    'capacityUsed',
    'idProduct',
    'productName',
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
  entrerProduit(es: EntreSortie,idBac:String){
    this.bacService.entrerProduit(es,idBac).subscribe({

    })
  }


  openEditForm() {

  }

  deleteProduct(idProduit: any) {

  }
}
