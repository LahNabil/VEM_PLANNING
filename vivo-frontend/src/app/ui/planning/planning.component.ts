import {Component, OnInit, ViewChild} from '@angular/core';
import { EntreSortie } from "../../models/EntreSortie";
import { Depot } from "../../models/Depot";
import { Product } from "../../models/Product";
import { Chart } from "angular-highcharts";
import { ProductService } from "../../Services/product.service";
import { ESService } from "../../Services/es.service";
import { Router } from "@angular/router";
import { DepotService } from "../../Services/depot.service";
import {PrevisionService} from "../../Services/prevision.service";
import {StockEsDto} from "../../models/StockEsDto";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-planning',
  templateUrl: './planning.component.html',
  styleUrl: './planning.component.scss'
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
  esList: EntreSortie[] = [];
  initialValue: number | undefined;
  selectedYear: number | undefined;
  selectedMonth: number | undefined;
  stockEsDto: StockEsDto = new StockEsDto();
  stockEsDtos: StockEsDto[] = [];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  constructor(private previsionService: PrevisionService,private produitService: ProductService, private esService: ESService, private router: Router, private depotService: DepotService) {}
  displayedColumns: string[] = [
    'dateJour',
    'stockInitial',
    'entre',
    'sortie',
    'stockFinale'
  ];
  ngOnInit() {
    this.getAllProducts();
    this.getAllDepots();
    this.initializeEmptyChart();
    this.getESParDepotProductDate();
  }
  getMontlyReport(){
    this.esService.generateMonthlyStockReport(this.selectedDepot,this.selectedProduct,this.selectedYear,this.selectedMonth).subscribe({
      next: (res)=>{
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
    })
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

    if (this.selectedDepot && this.selectedProduct && this.selectedYear && this.selectedMonth) {
      this.previsionService.calculerSommePByCityProduitDate(this.selectedDepot, this.selectedProduct, this.selectedYear, this.selectedMonth).subscribe(data => {
        console.log('Prevision data received: ', data);  // Log the received data
        this.previsionData = data;
      }, error => {
        console.error('Error fetching prevision data: ', error);  // Log any errors
      });
    }
  }
  getESParDepotProductDate(){
    if(this.selectedDepot && this.selectedProduct && this.selectedYear && this.selectedMonth){
      this.depotService.calculerStockDepotProduitDate(this.selectedDepot, this.selectedProduct,this.selectedYear,this.selectedMonth).subscribe(data=>{
        this.esList = data;
      })
    }
  }

  getSortieParDepotProductDate() {
    if (this.selectedDepot && this.selectedProduct) {
      this.depotService.calculerStockDepotProduitDate(this.selectedDepot, this.selectedProduct,this.selectedYear,this.selectedMonth).subscribe(data => {
        this.initialValue = data || 0;

        this.esService.getSortiesParProduitDepotDate(this.selectedDepot, this.selectedProduct,this.selectedYear,this.selectedMonth).subscribe((sorties: any[]) => {
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
          const safetyStockValue = 3000;
          const safetyStockLine = new Array(cumulativeQuantites.length).fill(safetyStockValue);

          this.lineChart = new Chart({
            chart: {
              type: 'line'
            },
            title: {
              text: 'Flux Mensuel des Stocks par Dépôt et Produit'
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
  initializeEmptyChart() {
    this.lineChart = new Chart({
      chart: {
        type: 'line'
      },
      title: {
        text: 'Flux Mensuel des Stocks par Dépôt et Produit'
      },
      credits: {
        enabled: false
      },
      xAxis: {
        categories: [],
        title: {
          text: 'Dates'
        }
      },
      yAxis: {
        title: {
          text: 'Quantités'
        },
        min: 0
      },
      series: [
        {
          type: 'line',
          name: 'ES',
          data: []
        },
        {
          type: 'line',
          name: 'Safety Stock',
          data: [],
          dashStyle: 'Dash',
          color: '#FF0000'
        }
      ]
    });
  }
  onSelectionChange() {
    this.getCapacityData();
    this.getStockData();
    this.getPrevisionData();
    this.getMontlyReport();

    if (this.selectedDepot && this.selectedProduct && this.selectedYear && this.selectedMonth) {
      this.getSortieParDepotProductDate();
    }
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
