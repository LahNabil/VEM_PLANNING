import {Component, Inject, OnInit} from '@angular/core';
import {DialogRef} from "@angular/cdk/dialog";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DepotService} from "../../Services/depot.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Depot} from "../../models/Depot";
import {Bac} from "../../models/Bac";
import {BacService} from "../../Services/bac.service";

@Component({
  selector: 'app-edit-bac',
  templateUrl: './edit-bac.component.html',
  styleUrl: './edit-bac.component.scss'
})
export class EditBacComponent implements OnInit{
  bac : Bac = new Bac();
  bacs: Bac[] = [];
  bacForm : FormGroup;

  constructor(private diologRef: DialogRef<EditBacComponent>,private formBuilder : FormBuilder, private bacService: BacService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any){
    this.bacForm = this.formBuilder.group({
      capacity: '',
      totalImpom: '',
      status: '',
      dateOuverture: ''
    })
  }
  ngOnInit() {
    this.bacForm.patchValue(this.data);
  }

  onFormSubmit() {
    this.bacService.editBac(this.data.idBac,this.bacForm.value).subscribe({
      next: (val:any)=>{
        alert('Bac Modifié avec succes');
        this.diologRef.close();
      },
      error: (err:any) =>{
        console.log(err);
      }
    })

  }
}
