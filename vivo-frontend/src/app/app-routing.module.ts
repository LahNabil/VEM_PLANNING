import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./ui/products/products.component";
import {ProductDetailsComponent} from "./ui/product-details/product-details.component";
import {BacComponent} from "./ui/bac/bac.component";
import {EntreSortieComponent} from "./ui/entre-sortie/entre-sortie.component";
import {DepotComponent} from "./ui/depot/depot.component";
import {DepotDetailsComponent} from "./ui/depot-details/depot-details.component";
import {StockDepotComponent} from "./ui/stock-depot/stock-depot.component";
import {PrevisionComponent} from "./ui/prevision/prevision.component";
import {DashboardComponent} from "./ui/dashboard/dashboard.component";
import {PlanningComponent} from "./ui/planning/planning.component";
import {SliderDepotStockComponent} from "./ui/slider-depot-stock/slider-depot-stock.component";

const routes: Routes = [

  {path: "products_details/:idProduit", component: ProductDetailsComponent},
  {path:'', redirectTo:'dashboard',pathMatch:'full'},
  {path:'dashboard', component: DashboardComponent},
  {path:'products', component:ProductsComponent},
  {path:'planning', component:PlanningComponent},
  {path:'salesforcast', component:PrevisionComponent},
  {path:'bac', component:BacComponent},
  {path:'depot', component:DepotComponent},
  {path:'stock', component:StockDepotComponent},
  {path:'entresortie', component:EntreSortieComponent},
  { path: 'depot-detail/:idDepot', component:DepotDetailsComponent},
  { path: 'test/:idDepot', component:SliderDepotStockComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
