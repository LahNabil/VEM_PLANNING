import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ProductService} from "../../Services/product.service";
import {Product} from "../../models/Product";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit{
  product: Product = new Product();
  products: Product[] = [];
  constructor(private productService : ProductService, private router: Router) {
  }
  ngOnInit(){
    this.getProducts();
  }
  getProducts(){
    this.productService.getProducts().subscribe(data=>{
      this.products = data;
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




}
