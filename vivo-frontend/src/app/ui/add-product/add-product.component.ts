import {Component, Inject, OnInit} from '@angular/core';
import {ProductService} from "../../Services/product.service";
import {RegimeService} from "../../Services/regime.service";
import {Router} from "@angular/router";
import {Regime} from "../../models/Regime";
import {Product} from "../../models/Product";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DialogRef} from "@angular/cdk/dialog";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

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

  Products: string[] = ['Gasoil SH','Gasoil','Gasoil SHL','Gasoil SHL','Gasoil SHD','Gasoil PE','SSP','SSP SH','SSP SD','SSP SHD','SSP DD','Jet A1',]


  constructor(private diologRef: DialogRef<AddProductComponent>,private formBuilder : FormBuilder,private productService : ProductService, private regimeService: RegimeService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any) {
    console.log(data);
    this.productForm = this.formBuilder.group({
      name: '',
      status: '',
      type: '',
      regime: ''
      // regime: this.formBuilder.group({
      //  idRegime: // Set initial value to null
      //   })
    });
  }
  getRegimes(){
    this.regimeService.getRegimes().subscribe(dataRegime=>{
      this.regimes = dataRegime;
    })
  }
  getProducts(){
    this.productService.getProducts().subscribe(data=>{
      this.products = data;
      console.log(data);
    })
  }

  ngOnInit() {
    this.getRegimes();
    this.productForm.patchValue(this.data);

  }

  onFormSubmit() {
    if(this.data){
      if(this.productForm.valid){
        this.productService.editProduct(this.data.idProduit,this.productForm.value).subscribe({
          next: (val:any)=>{
            alert('Produit Modifié avec succes');
            this.diologRef.close();
          },
          error: (err:any) =>{
            console.log(err);
          }
        })
      }
    } else {
      if(this.productForm.valid){
        this.productService.addProduct(this.productForm.value).subscribe({
          next: (val:any)=>{
            alert('Produit ajouté avec succes');
            this.diologRef.close();
          },
          error: (err:any) =>{
            console.log(err);
          }
        })
      }
    }

  }
}
