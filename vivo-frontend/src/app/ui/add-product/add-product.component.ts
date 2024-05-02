import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../Services/product.service";
import {RegimeService} from "../../Services/regime.service";
import {Router} from "@angular/router";
import {Regime} from "../../models/Regime";
import {Product} from "../../models/Product";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DialogRef} from "@angular/cdk/dialog";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.scss'
})
export class AddProductComponent implements OnInit{
  regimes : Regime[] = [];
  regime: Regime = new Regime();
  products: Product[] = [];
  product: Product = new Product();
  productForm: FormGroup;

  constructor(private diologRef: DialogRef<AddProductComponent>,private formBuilder : FormBuilder,private productService : ProductService, private regimeService: RegimeService, private router : Router) {
    this.productForm = this.formBuilder.group({
      name: '',
      status: '',
      type: '',
      regime: this.formBuilder.group({
        idRegime: [1] // Set initial value to null
      })
    });
  }
  getRegimes(){
    this.regimeService.getRegimes().subscribe(data=>{
      this.regimes = data;
    })
  }
  getProducts(){
    this.productService.getProducts().subscribe(data=>{
      this.products = data;
    })
  }

  ngOnInit() {
    this.getRegimes();

  }

  onFormSubmit() {
    if(this.productForm.valid){
      this.productService.addProduct(this.productForm.value).subscribe({
        next: (val:any)=>{
          alert('Produit ajouté avec succes');
          this.diologRef.close();
        },
        error: (err:any) =>{
          console.log(err);
          console.log(this.productForm.value);
        }
      })
    }
  }
}
