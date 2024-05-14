import {Component, OnInit} from '@angular/core';
import {DepotService} from "../../Services/depot.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-depot-details',
  templateUrl: './depot-details.component.html',
  styleUrl: './depot-details.component.scss'
})
export class DepotDetailsComponent implements OnInit{

  constructor(private depotService : DepotService,private router : Router) {
  }
  ngOnInit() {
  }


}
