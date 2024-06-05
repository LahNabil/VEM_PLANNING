import {Component, Inject, OnInit} from '@angular/core';
import {Depot} from "../../models/Depot";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DialogRef} from "@angular/cdk/dialog";
import {DepotService} from "../../Services/depot.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Prevision} from "../../models/Prevision";
import {PrevisionService} from "../../Services/prevision.service";
import {ProductService} from "../../Services/product.service";
import {Product} from "../../models/Product";

@Component({
  selector: 'app-add-prevision',
  templateUrl: './add-prevision.component.html',
  styleUrl: './add-prevision.component.scss'
})
export class AddPrevisionComponent implements OnInit{
  prevision : Prevision = new Prevision();
  previsions: Prevision[] = [];
  products: Product[] = [];
  product: Product = new Product();
  previsionForm : FormGroup;
  villes: string[] = ['Mohammedia','Casablanca','Agadir','TanTan','Nador J.V','Sidi Kacem J.V','Mohammedia J.V','Laayoune J.v','Dakhla J.V']

  constructor(private diologRef: DialogRef<AddPrevisionComponent>,private formBuilder : FormBuilder,private productService: ProductService, private previsionService: PrevisionService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any){
    this.previsionForm = this.formBuilder.group({
      foreCaste: '',
      date: '',
      business: '',
      nameProduct: '',
      supplyEnveloppe:''
    })
  }
  ngOnInit() {
    this.previsionForm.patchValue(this.data);
    this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getProducts().subscribe(data=>{
      this.products = data;
    })
  }
  onFormSubmit() {
    if(this.data){
      if(this.previsionForm.valid){
        this.previsionService.editPrevision(this.data.idPrevision,this.previsionForm.value).subscribe({
          next: (val:any)=>{
            alert('Prevision Modifiée avec succes');
            this.diologRef.close();
          },
          error: (err:any) =>{
            console.log(err);
          }
        })
      }
    } else {
      if(this.previsionForm.valid){
        this.previsionService.addPrevision(this.previsionForm.value).subscribe({
          next: (val:any)=>{
            alert('Prevision ajoutée avec succes');
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
