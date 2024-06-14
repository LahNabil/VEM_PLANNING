import {Component, OnInit} from '@angular/core';
import {ESService} from "../../../Services/es.service";
import {Product} from "../../../models/Product";
import {EntreSortie} from "../../../models/EntreSortie";
import {Prevision} from "../../../models/Prevision";
import {PrevisionService} from "../../../Services/prevision.service";

@Component({
  selector: 'app-top-section',
  templateUrl: './top-section.component.html',
  styleUrl: './top-section.component.scss'
})
export class TopSectionComponent implements OnInit{
  ES: EntreSortie = new EntreSortie();
  ESs: EntreSortie[] = [];
  previson :Prevision = new Prevision();
  previsions: Prevision[]=[];
  totalEntree: number = 0;
  totalSortie: number = 0;
  previsionNextM: number = 0;
  constructor(private EntreSortieService : ESService , private previsionService: PrevisionService) {
  }
  ngOnInit() {
    this.getEntreeBymonth();
    this.getSortieBymonth();
    this.getPrevisionNextMonth();
  }
  getPrevisionNextMonth(){
    this.previsionService.getPrevisionNextMonth().subscribe((data:number)=>{
      this.previsionNextM = data;
    });
  }
  getEntreeBymonth() {
    this.EntreSortieService.getEntreeBymonth().subscribe((data: number) => {
      this.totalEntree = data;
    });
  }

  getSortieBymonth(){
    this.EntreSortieService.getSortieByMonth().subscribe((data: number) => {
      this.totalSortie = data;
    });
  }

}
