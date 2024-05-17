import {Component, Inject, OnInit} from '@angular/core';
import {Depot} from "../../models/Depot";
import {DialogRef} from "@angular/cdk/dialog";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ProductService} from "../../Services/product.service";
import {RegimeService} from "../../Services/regime.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {DepotService} from "../../Services/depot.service";

@Component({
  selector: 'app-add-depot',
  templateUrl: './add-depot.component.html',
  styleUrl: './add-depot.component.scss'
})
export class AddDepotComponent implements OnInit{
  depot : Depot = new Depot();
  depots: Depot[] = [];
  depotForm : FormGroup;
  villes: string[] = ['Mohammedia','Casablanca','Agadir','TanTan','Nador J.V','Sidi Kacem J.V','Mohammedia J.V','Laayoune J.v','Dakhla J.V']

  constructor(private diologRef: DialogRef<AddDepotComponent>,private formBuilder : FormBuilder, private depotService: DepotService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any){
    this.depotForm = this.formBuilder.group({
      idDepot: '',
      nameDepot: '',
      zone: '',
      area: ''
    })
  }
  ngOnInit() {
    this.depotForm.patchValue(this.data);
  }
  getAllDepots(){
    this.depotService.getDepots().subscribe()
  }

  onFormSubmit() {
    if(this.data){
      if(this.depotForm.valid){
        this.depotService.editDepot(this.data.idDepot,this.depotForm.value).subscribe({
          next: (val:any)=>{
            alert('Depot Modifié avec succes');
            this.diologRef.close();
          },
          error: (err:any) =>{
            console.log(err);
          }
        })
      }
    } else {
      if(this.depotForm.valid){
        this.depotService.addDepot(this.depotForm.value).subscribe({
          next: (val:any)=>{
            alert('Depot ajouté avec succes');
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
