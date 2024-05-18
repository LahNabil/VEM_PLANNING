import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashbordComponent} from "./ui/dashbord/dashbord.component";
import {UsersComponent} from "./ui/users/users.component";
import {ProductsComponent} from "./ui/products/products.component";
import {PlanningComponent} from "./ui/planning/planning.component";
import {SalesforcastComponent} from "./ui/salesforcast/salesforcast.component";
import {DepotComponent} from "./ui/depot/depot.component";
import {StockComponent} from "./ui/stock/stock.component";
import {StockDepotComponent} from "./ui/stock-depot/stock-depot.component";
import {BacComponent} from "./ui/bac/bac.component";
import {EntreSortieComponent} from "./ui/entre-sortie/entre-sortie.component";


const routes: Routes = [
  {path:'', redirectTo:'dashboard',pathMatch:'full'},
  {path:'dashboard', component:DashbordComponent},
  {path:'users', component:UsersComponent},
  {path:'products', component:ProductsComponent},
  {path:'planning', component:PlanningComponent},
  {path:'salesforcast', component:SalesforcastComponent},
  {path:'bac', component:BacComponent},
  {path:'depot', component:DepotComponent},
  {path:'stock', component:StockDepotComponent},
  {path:'entresortie', component:EntreSortieComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
