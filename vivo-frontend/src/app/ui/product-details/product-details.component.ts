import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {Product} from "../../models/Product";
import {ProductService} from "../../Services/product.service";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.scss'
})
export class ProductDetailsComponent implements OnInit{
  product : Product = new Product();
  idProduit : number| undefined;

  constructor(private productService : ProductService, private route : ActivatedRoute, private router : Router){}
  goToVoitureHome(){
    this.router.navigate(['/'])
  }
  ngOnInit() {
    this.idProduit = this.route.snapshot.params['idProduit'];
    if(this.idProduit !== undefined) {
      this.productService.getProductById(this.idProduit).subscribe(data => {
        this.product = data;
      })
    }else{
      console.error("id incorrect")
    }
  }

}
