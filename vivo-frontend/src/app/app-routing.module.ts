import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./ui/products/products.component";
import {ProductDetailsComponent} from "./ui/product-details/product-details.component";
import {BacComponent} from "./ui/bac/bac.component";

const routes: Routes = [
  {path: "products",component: ProductsComponent},
  {path: "products_details/:idProduit", component: ProductDetailsComponent},
  {path: "bac", component: BacComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
