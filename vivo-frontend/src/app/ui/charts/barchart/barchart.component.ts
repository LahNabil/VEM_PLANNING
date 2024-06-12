import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { DepotService } from '../../../Services/depot.service';
import { Depot } from '../../../models/Depot';

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

  chartOptions: any;

  constructor(private depotService: DepotService) {}

  ngOnInit() {
    this.loadDepotData();
  }

  loadDepotData() {
    // Load all depots, capacities, and stock data before updating the chart
    this.depotService.getDepots().subscribe(depots => {
      this.depots = depots;
      this.categories = this.depots.map(depot => depot.nameDepot).filter(name => name !== undefined) as string[];

      this.loadCapacityData();
      this.loadStockData();
    });
  }

  loadCapacityData() {
    this.depotService.calculerCapacities().subscribe(capacities => {
      this.capacityData = capacities;
      this.updateChartIfReady();
    });
  }

  loadStockData() {
    this.depotService.calculerStocks().subscribe(stocks => {
      this.stockData = stocks;
      this.updateChartIfReady();
    });
  }

  updateChartIfReady() {
    // Ensure that both capacity and stock data are loaded before updating the chart
    if (this.capacityData.length > 0 && this.stockData.length > 0) {
      this.updateChart();
    }
  }

  updateChart() {
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
