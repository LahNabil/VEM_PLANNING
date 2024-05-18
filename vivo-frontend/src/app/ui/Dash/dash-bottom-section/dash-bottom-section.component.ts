import {Component, OnInit} from '@angular/core';
import {Depot} from "../../../models/Depot";
import {MatTableDataSource} from "@angular/material/table";
import {DepotService} from "../../../Services/depot.service";
import {ProductService} from "../../../Services/product.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-dash-bottom-section',
  templateUrl: './dash-bottom-section.component.html',
  styleUrl: './dash-bottom-section.component.scss'
})
export class DashBottomSectionComponent  implements OnInit{
  depot: Depot = new Depot();
  depots: Depot[] = [];
  constructor(private productService: ProductService, private depotService: DepotService, private router: Router, private _dialog: MatDialog) {
  }
  getAllDepots() {
    this.depotService.getDepots().subscribe({
      next: (res) => {
        res.forEach((depot: Depot) => {
          this.depotService.calculerStock(depot.idDepot).subscribe({
            next: (stock) => {
              depot.stock = stock;
              console.log(res); // This should log the complete response
            }
          });
          depot.bacDtos?.forEach(bac => {
            this.productService.getProductById(bac.idProduct).subscribe({
              next: (product) => {
                bac.product = product;
              }
            });
          });
        });
        this.depots = res; // Make sure to assign the response to this.depots
      },
      error: (err) => {
        console.error('Error fetching depots:', err);
      }
    });
  }

  ngOnInit() {
    this.getAllDepots();
  }


  searchText = '';
}
