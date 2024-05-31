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
    'foreCaste',
    'vReel',
    'business',
    'date',
    'nameProduct',
    'supplyEnveloppe',
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
    this._dialog.open(AddProductComponent,{
      data,
    });
  }

  getPrevisions() {
    this.previsionService.getPrevision().subscribe({
      next: (previsions) => {
        previsions.forEach((prevision: Prevision) => {
          this.previsionService.calculerVreel(prevision.idPrevision).subscribe({
            next: (vReel) => {
              prevision.vReel = vReel;
            }
          });
        });
        this.dataSource = new MatTableDataSource(previsions);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
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

}
