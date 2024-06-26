import {Component, OnInit} from '@angular/core';
import {EntreSortie} from "../../../models/EntreSortie";
import {Depot} from "../../../models/Depot";
import {Product} from "../../../models/Product";
import {Chart} from "angular-highcharts";
import {ProductService} from "../../../Services/product.service";
import {ESService} from "../../../Services/es.service";
import {Router} from "@angular/router";
import {DepotService} from "../../../Services/depot.service";

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrl: './line-chart.component.scss'
})
export class LineChartComponent implements OnInit {
  selectedDepot: string | undefined;
  selectedProduct: string | undefined;
  sortie: EntreSortie[] = [];
  depot: Depot = new Depot();
  product: Product = new Product();
  products: Product[] = [];
  depots: Depot[] = [];
  lineChart!: Chart
  initialValue: number|undefined;
  // lineChart!: Chart; // Utilisation de l'opérateur "!" pour indiquer que la propriété sera initialisée

  constructor(private produitService: ProductService, private esService: ESService, private router: Router, private depotService: DepotService) {
  }



  ngOnInit() {
    this.getAllProducts();
    this.getAllDepots();
    this.getSortieParDepotProduct();
    this.initializeChartWithStaticData();

  }

  getAllDepots() {
    this.depotService.getDepots().subscribe(data => {
      this.depots = data;
    })
  }

  getAllProducts() {
    this.produitService.getProducts().subscribe(data => {
      this.products = data;
    })
  }


  getSortieParDepotProduct() {
    this.depotService.calculerStockDepotProduit(this.selectedDepot, this.selectedProduct).subscribe(data => {
      if (data !== undefined) {
        this.initialValue = data;
      } else {
        this.initialValue = 0; // ou une autre valeur par défaut si nécessaire
      }

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
            }
          ]
        });
      });
    });
  }
  onSelectionChange() {
    if (this.selectedDepot && this.selectedProduct) {
      this.getSortieParDepotProduct();
    }
  }
  initializeChartWithStaticData() {
    const staticDates = ['2023-01-01', '2023-02-01', '2023-03-01', '2023-04-01'];
    const staticQuantites = [10, 15, 20, 25];

    this.lineChart = new Chart({
      chart: {
        type: 'line'
      },
      title: {
        text: 'Initial Linechart '
      },
      credits: {
        enabled: false
      },
      xAxis: {
        categories: staticDates,
        title: {
          text: 'Dates'
        }
      },
      yAxis: {
        title: {
          text: 'Quantités'
        }
      },
      series: [
        {
          type: 'line',
          name: 'Static Data',
          data: staticQuantites
        }
      ]
    });
  }


  // generateLineChart() {

// }

}
