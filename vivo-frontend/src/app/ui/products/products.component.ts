import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ProductService} from "../../Services/product.service";
import {Product} from "../../models/Product";
import {MatDialog} from "@angular/material/dialog";
import {AddProductComponent} from "../add-product/add-product.component";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit{
  product: Product = new Product();
  products: Product[] = [];
  constructor(private productService : ProductService, private router: Router, private _dialog: MatDialog) {
  }
  displayedColumns: string[] = [
    'idProduit',
    'name',
    'status',
    'type',
    'regime',
    'actions'
  ];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  ngOnInit(){
    this.getProducts();
  }
  openAddForm(){
    this._dialog.open(AddProductComponent);
  }
  openEditForm(data: any){
    this._dialog.open(AddProductComponent,{
      data,
    });
  }

  getProducts(){
    this.productService.getProducts().subscribe({
      next: (res)=>{
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
    })
  }
  deleteProduct(idProduit: number|undefined){
    const isConfirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette assurance ?");
    if (isConfirmed) {
      this.productService.deleteProduct(idProduit).subscribe(data => {
        window.location.reload();
      });
    }
  }
  productDetail(idProduit:number|undefined){
    if(idProduit !== undefined){
      this.router.navigate(['products_details', idProduit])
    }else{
      console.error("Id incorrect")
    }


  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }




}
