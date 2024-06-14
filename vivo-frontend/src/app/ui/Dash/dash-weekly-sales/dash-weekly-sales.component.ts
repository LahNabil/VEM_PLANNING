import { Component } from '@angular/core';
import {Chart} from "angular-highcharts";

@Component({
  selector: 'app-dash-weekly-sales',
  templateUrl: './dash-weekly-sales.component.html',
  styleUrl: './dash-weekly-sales.component.scss'
})
export class DashWeeklySalesComponent {
  chart = new Chart({
    chart:{
      type: 'column',
      height:200
    },
    title:{
      text:'weekly sales',
      style: {
        fontSize: '12px'
      }

    },
    xAxis:{
      categories:[
        'Week 1 (1st)', 'Week 2 (8th)', 'Week 3 (15th)', 'Week 4 (22nd)'
      ]
    },
    yAxis:{
      title:{
        text:'quantite de stock'
      }
    },
    series:[
      {
        name:"ssp",
        type:"column",
        data:[60,40,50,70],
        color:'blue'
      },
      {
        name:"gasoil",
        type:"column",
        data:[200,20,50,30],
        color:'green'
      },
      {
        name:"jet",
        type:"column",
        data:[100,20,60,77],
        color:'red'
      }
    ]
  })
}
