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
import {AuthGuard} from "./guards/auth.guard";
import {WelcomePageComponent} from "./ui/welcome-page/welcome-page.component";

const routes: Routes = [

  {path: "products_details/:idProduit", component: ProductDetailsComponent},
  {path:'', redirectTo:'welcome',pathMatch:'full'},
  {path: 'welcome',component:WelcomePageComponent},
  {path:'dashboard', component: DashboardComponent, canActivate:[AuthGuard], data: {roles:["USER"]}},
  {path:'products', component:ProductsComponent, canActivate:[AuthGuard], data: {roles:["USER"]}},
  {path:'planning', component:PlanningComponent, canActivate:[AuthGuard], data: {roles:["USER"]}},
  {path:'salesforcast', component:PrevisionComponent, canActivate:[AuthGuard], data: {roles:["ADMIN"]}},
  {path:'bac', component:BacComponent,canActivate:[AuthGuard], data: {roles:["ADMIN"]}},
  {path:'depot', component:DepotComponent,canActivate:[AuthGuard], data: {roles:["USER"]}},
  {path:'stock', component:StockDepotComponent,canActivate:[AuthGuard], data: {roles:["USER"]}},
  {path:'entresortie', component:EntreSortieComponent,canActivate:[AuthGuard], data: {roles:["USER"]}},
  { path: 'depot-detail/:idDepot', component:DepotDetailsComponent,canActivate:[AuthGuard], data: {roles:["USER"]}},
  { path: 'test/:idDepot', component:SliderDepotStockComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
