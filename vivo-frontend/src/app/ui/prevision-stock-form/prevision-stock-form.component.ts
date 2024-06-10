import {Component, Inject, OnInit} from '@angular/core';
import {IsStock} from "../../models/IsStock";
import {DialogRef} from "@angular/cdk/dialog";
import {FormBuilder} from "@angular/forms";
import {BacService} from "../../Services/bac.service";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {PrevisionService} from "../../Services/prevision.service";

@Component({
  selector: 'app-prevision-stock-form',
  templateUrl: './prevision-stock-form.component.html',
  styleUrl: './prevision-stock-form.component.scss'
})
export class PrevisionStockFormComponent implements OnInit{
  isStock: IsStock = new IsStock();

  constructor(private diologRef: DialogRef<PrevisionStockFormComponent>,private formBuilder : FormBuilder, private previsionService: PrevisionService, private router : Router, @Inject(MAT_DIALOG_DATA) public data: any) {
  }
  ngOnInit() {
    this.getIsStock();

  }

  getIsStock(){
    this.previsionService.isStockSuffisant(this.data.idPrevision).subscribe(stock=>{
      this.isStock = stock;
    })

  }

}
