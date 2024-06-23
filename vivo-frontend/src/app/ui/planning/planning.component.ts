import { Component, OnInit } from '@angular/core';
import { EntreSortie } from "../../models/EntreSortie";
import { Depot } from "../../models/Depot";
import { Product } from "../../models/Product";
import { Chart } from "angular-highcharts";
import { ProductService } from "../../Services/product.service";
import { ESService } from "../../Services/es.service";
import { Router } from "@angular/router";
import { DepotService } from "../../Services/depot.service";
import {PrevisionService} from "../../Services/prevision.service";

@Component({
  selector: 'app-planning',
  templateUrl: './planning.component.html',
  styleUrls: ['./planning.component.scss']
})
export class PlanningComponent implements OnInit {
  selectedDepot: string | undefined;
  selectedProduct: string | undefined;
  capacity: number | undefined;
  stockDepot: number | undefined;
  previsionData: number | undefined;
  sortie: EntreSortie[] = [];
  depot: Depot = new Depot();
  product: Product = new Product();
  products: Product[] = [];
  depots: Depot[] = [];
  lineChart!: Chart;
  initialValue: number | undefined;
  selectedYear: number | undefined;
  selectedMonth: number | undefined;

  constructor(private previsionService: PrevisionService,private produitService: ProductService, private esService: ESService, private router: Router, private depotService: DepotService) {}

  ngOnInit() {
    this.getAllProducts();
    this.getAllDepots();
  }

  getAllDepots() {
    this.depotService.getDepots().subscribe(data => {
      this.depots = data;
    });
  }

  getAllProducts() {
    this.produitService.getProducts().subscribe(data => {
      this.products = data;
    });
  }

  getCapacityData() {
    if (this.selectedDepot) {
      this.depotService.calculerCapacityDepot(this.selectedDepot).subscribe(capacity => {
        this.capacity = capacity;
      });
    }
  }

  getStockData() {
    if (this.selectedDepot && this.selectedProduct && this.selectedYear && this.selectedMonth) {
      this.depotService.calculerStockDepotProduitDate(this.selectedDepot, this.selectedProduct,this.selectedYear,this.selectedMonth).subscribe(data => {
        this.stockDepot = data;
      });
    }
  }
  getPrevisionData() {
    console.log('Selected Depot:', this.selectedDepot);  // Log selectedDepot
    console.log('Selected Product:', this.selectedProduct);  // Log selectedProduct
    console.log('Selected Year:', this.selectedYear);  // Log selectedYear
    console.log('Selected Month:', this.selectedMonth);  // Log selectedMonth

    if (this.selectedDepot && this.selectedProduct && this.selectedYear && this.selectedMonth) {
      this.previsionService.calculerSommePByCityProduitDate(this.selectedDepot, this.selectedProduct, this.selectedYear, this.selectedMonth).subscribe(data => {
        console.log('Prevision data received: ', data);  // Log the received data
        this.previsionData = data;
      }, error => {
        console.error('Error fetching prevision data: ', error);  // Log any errors
      });
    }
  }

  getSortieParDepotProduct() {
    if (this.selectedDepot && this.selectedProduct) {
      this.depotService.calculerStockDepotProduit(this.selectedDepot, this.selectedProduct).subscribe(data => {
        this.initialValue = data || 0;

        this.esService.getSortiesParProduitDepot(this.selectedDepot, this.selectedProduct).subscribe((sorties: any[]) => {
          const dates = sorties.map(sortie => sortie.date);
          const quantites = sorties.map(sortie => sortie.quantite);

          let cumulativeQuantites = quantites.reduce((acc, qty, index) => {
            if (index === 0) {
              acc.push(this.initialValue + qty);
            } else {
              acc.push(acc[index - 1] + qty);
            }
            return acc;
          }, []);
          const safetyStockValue = 5000;
          const safetyStockLine = new Array(cumulativeQuantites.length).fill(safetyStockValue);

          this.lineChart = new Chart({
            chart: {
              type: 'line'
            },
            title: {
              text: 'Linechart'
            },
            credits: {
              enabled: false
            },
            xAxis: {
              categories: dates,
              title: {
                text: 'Dates'
              }
            },
            yAxis: {
              title: {
                text: 'Quantités'
              },
              min: Math.min(1000, ...cumulativeQuantites)
            },
            series: [
              {
                type: 'line',
                name: 'ES',
                data: cumulativeQuantites
              },
              {
                type: 'line',
                name: 'Safety Stock',
                data: safetyStockLine,
                dashStyle: 'Dash',
                color: '#FF0000'
              }
            ]
          });
        });
      });
    }
  }

  onSelectionChange() {
    this.getCapacityData();
    this.getStockData();
    this.getPrevisionData();
  }
}
