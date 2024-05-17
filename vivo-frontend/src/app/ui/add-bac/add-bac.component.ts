import {Component, Inject, OnInit} from '@angular/core';
import {Bac} from "../../models/Bac";
import {DialogRef} from "@angular/cdk/dialog";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ProductService} from "../../Services/product.service";
import {RegimeService} from "../../Services/regime.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {BacService} from "../../Services/bac.service";
import {Product} from "../../models/Product";
import {Depot} from "../../models/Depot";
import {DepotService} from "../../Services/depot.service";

@Component({
  selector: 'app-add-bac',
  templateUrl: './add-bac.component.html',
  styleUrl: './add-bac.component.scss'
})
export class AddBacComponent implements OnInit{

  bac : Bac = new Bac();
  bacs : Bac[] = [];
  product: Product = new Product();
  products: Product[] = [];
  depot : Depot = new Depot();
  depots : Depot[] = [];
  bacForm : FormGroup;

  constructor(private diologRef: DialogRef<AddBacComponent>,private formBuilder : FormBuilder,private depotService : DepotService,private productService : ProductService, private bacService: BacService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.bacForm = this.formBuilder.group({
      idBac: '',
      capacity: '',
      totalImpom: '',
      status: '',
      dateOuverture: '',
      capacityUsed: '',
      idProduct: '',
      idDepot: ''
      // product: this.formBuilder.group({
      //   idProduit: [this.data.idProduit] // Set initial value to null
      // }),
      // depot: this.formBuilder.group({
      //   idDepot: [this.data.idDepot] // Set initial value to null
      // })
    });
  }
  ngOnInit() {
    this.getAllDepots();
    this.getAllProducts();
  }
  getAllDepots(){
    this.depotService.getDepots().subscribe(data=>{
      this.depots = data;
    })
  }
  getAllProducts(){
    this.productService.getProducts().subscribe(data=>{
      this.products = data;
    })
  }

  onFormSubmit() {
    this.bacService.addBac(this.bacForm.value).subscribe({
      next: (val:any)=>{
        alert('Bac ajouté avec succes');
        this.diologRef.close();
      },
      error: (err:any) =>{
        console.log(err);
      }
    })

  }
}
