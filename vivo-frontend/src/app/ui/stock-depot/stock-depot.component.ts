import {Component, OnInit} from '@angular/core';
import {DepotService} from "../../Services/depot.service";
import {Route, Router} from "@angular/router";
import {Depot} from "../../models/Depot";
import {StockProduitDto} from "../../models/StockProduitDto";

@Component({
  selector: 'app-stock-depot',
  templateUrl: './stock-depot.component.html',
  styleUrl: './stock-depot.component.scss'
})
export class StockDepotComponent implements OnInit{
  depots : Depot[] = [];
  depot : Depot = new Depot();
  stocks: { [key: string]: StockProduitDto[] } = {};

  constructor(private depotService:DepotService, private router: Router) {
  }
  ngOnInit() {
    this.getDepots();
  }
  getDepots(): void {
    this.depotService.getDepots().subscribe(
      depots => {
        this.depots = depots;
        this.depots.forEach(depot => {
          this.getStockForDepot((depot.idDepot).toString());
        });
      },
      error => {
        console.error('Error fetching depots:', error);
      }
    );
  }
  getStockForDepot(idDepot: string){
    this.depotService.calculerStocksProduits(idDepot).subscribe(
      stocks=>{
        this.stocks[idDepot] = stocks;
      },
      error => {
        console.error(`Error fetching stocks for depot ${idDepot}:`, error);
      }
    )
  }

}
