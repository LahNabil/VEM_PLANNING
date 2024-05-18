import {Component, OnInit} from '@angular/core';
import {DepotService} from "../../Services/depot.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Depot} from "../../models/Depot";

@Component({
  selector: 'app-depot-details',
  templateUrl: './depot-details.component.html',
  styleUrl: './depot-details.component.scss'
})
export class DepotDetailsComponent implements OnInit{
  idDepot : String|undefined;
  depot : Depot = new Depot();

  constructor(private depotService : DepotService,private router : Router, private route : ActivatedRoute) {
  }
  ngOnInit() {
    this.idDepot = this.route.snapshot.params['idDepot'];
    if (this.idDepot !== undefined) {
      this.depotService.getDepotById(this.idDepot).subscribe(data => {
        this.depot = data;
        this.depotService.calculerStock(this.idDepot).subscribe({ // Corrected from 'depot.idDepot' to 'this.idDepot'
          next: (stock) => {
            this.depot.stock = stock; // Corrected from 'depot.stock' to 'this.depot.stock'
          }
        });
      }, error => {
        console.error("Error fetching depot:", error);
      });
    } else {
      console.error("id incorrect");
    }
  }



}
