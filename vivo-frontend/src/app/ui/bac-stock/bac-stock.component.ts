import {Component, Inject, OnInit} from '@angular/core';
import {Regime} from "../../models/Regime";
import {Product} from "../../models/Product";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DialogRef} from "@angular/cdk/dialog";
import {ProductService} from "../../Services/product.service";
import {RegimeService} from "../../Services/regime.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Bac} from "../../models/Bac";
import {BacService} from "../../Services/bac.service";

@Component({
  selector: 'app-bac-stock',
  templateUrl: './bac-stock.component.html',
  styleUrl: './bac-stock.component.scss'
})
export class BacStockComponent implements OnInit{
  bacs : Bac[] = [];
  bac: Bac = new Bac();

  bacForm: FormGroup;

  constructor(private diologRef: DialogRef<BacStockComponent>,private formBuilder : FormBuilder,private bacService : BacService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.bacForm = formBuilder.group({
      quantite: '',
      date: '',
      typeES: '',
      bac: this.formBuilder.group({
        idBac: ["12345"] // Set initial value to null
      })

      })
  }
  ngOnInit() {
    this.getBacs();
    this.bacForm.patchValue(this.data);
  }

  getBacs(){
    this.bacService.getBac().subscribe(data =>{
      this.bacs = data;
    })

  }
  onFormSubmit() {
    if(this.bacForm.valid){
      this.bacService.stockProduit(this.bacForm.value, this.data.idBac).subscribe({
        next: (val:any)=>{
          alert('Quantité Modifié avec succes');
          this.diologRef.close();
          console.log(val);
        },
        error: (err:any) =>{
          console.log(err);
          console.log(this.bacForm.value);
        }
      })
    }

  }


}
