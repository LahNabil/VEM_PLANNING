import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { DepotService } from "../../../Services/depot.service";
import { Depot } from "../../../models/Depot";

@Component({
  selector: 'app-barchart',
  templateUrl: './barchart.component.html',
  styleUrls: ['./barchart.component.scss']
})
export class BarchartComponent implements OnInit {
  Highcharts: any = Highcharts;
  depots: Depot[] = [];
  categories: string[] = [];
  capacityData: number[] = [];
  stockData: number[] = [];

  constructor(private depotService: DepotService) {}

  ngOnInit() {
    this.getAllDepot();
  }

  getAllDepot() {
    this.depotService.getDepots().subscribe(data => {
      this.depots = data;
      if (this.categories.length === 0) {
        this.populateChartData();
      }
    });
  }

  populateChartData() {
    this.categories = this.depots.map(depot => depot.nameDepot).filter(category => category !== undefined) as string[];

    // Clearing the arrays before adding new data
    this.capacityData = [];
    this.stockData = [];

    this.depots.forEach(depot => {
      const idDepot = depot.idDepot;
      this.depotService.calculerCapacity(idDepot).subscribe(capacity => {
        this.capacityData.push(capacity);
        this.updateChart();
      });
      this.depotService.calculerStocksProduits(idDepot).subscribe(stock => {
        this.stockData.push(stock);
        this.updateChart();
      });
    });
  }

  updateChart() {
    if (this.capacityData.length === this.depots.length && this.stockData.length === this.depots.length) {
      this.chartOptions = {
        chart: {
          type: 'column'
        },
        title: {
          text: 'Capacity vs Stock per Depot'
        },
        xAxis: {
          categories: this.categories
        },
        yAxis: {
          title: {
            text: 'Values'
          }
        },
        series: [
          {
            name: 'Capacity',
            data: this.capacityData
          },
          {
            name: 'Stock',
            data: this.stockData
          }
        ]
      };
    }
  }

  chartOptions: any;
}
