import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./ui/products/products.component";
import {ProductDetailsComponent} from "./ui/product-details/product-details.component";

const routes: Routes = [
  {path: "products",component: ProductsComponent},
  {path: "products_details/:idProduit", component: ProductDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
