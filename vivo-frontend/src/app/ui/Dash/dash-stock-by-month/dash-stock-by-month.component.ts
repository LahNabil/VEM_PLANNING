import { Component } from '@angular/core';
import {Chart} from "angular-highcharts";

@Component({
  selector: 'app-dash-stock-by-month',
  templateUrl: './dash-stock-by-month.component.html',
  styleUrl: './dash-stock-by-month.component.scss'
})
export class DashStockByMonthComponent {
  chart = new Chart({
    chart:{
      type: 'line',
      height:200
    },
    title:{
      text:'stocks by month',
      style: {
        fontSize: '12px'
      }

    },
    xAxis:{
      categories:[
        'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
      ]
    },
    yAxis:{
      title:{
        text:'quantite de stock'
      }
    },
    series:[
      {
        name:"stock security",
        type:"line",
        data:[60,40,50,70,150],
        color:'blue'
      },
      {
        name:"stock safety",
        type:"line",
        data:[200,20,50,30,10],
        color:'green'
      },
      {
        name:"stock ",
        type:"line",
        data:[100,20,60,77,11],
        color:'red'
      }
    ]
  })
  ngOnInit() {
  }
}
