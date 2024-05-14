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

  openEditForm(data: any) {
    this._dialog.open(AddProductComponent, {
      data,
    });
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

  openAddForm() {

  }
}

