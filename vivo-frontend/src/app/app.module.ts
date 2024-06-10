import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsComponent } from './ui/products/products.component';
import {HeaderComponent} from "./ui/header/header.component";
import { ProductDetailsComponent } from './ui/product-details/product-details.component';
import { AddProductComponent } from './ui/add-product/add-product.component';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatDialogModule} from "@angular/material/dialog";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {MatRadioModule} from "@angular/material/radio";
import {MatNativeDateModule} from "@angular/material/core";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatToolbarModule} from "@angular/material/toolbar";
import { BacComponent } from './ui/bac/bac.component';
import { BacStockComponent } from './ui/bac-stock/bac-stock.component';
import { EntreSortieComponent } from './ui/entre-sortie/entre-sortie.component';
import { DepotComponent } from './ui/depot/depot.component';
import { DepotDetailsComponent } from './ui/depot-details/depot-details.component';
import { AddBacComponent } from './ui/add-bac/add-bac.component';
import { StockDepotComponent } from './ui/stock-depot/stock-depot.component';
import { AddDepotComponent } from './ui/add-depot/add-depot.component';
import { PrevisionComponent } from './ui/prevision/prevision.component';
import { AddPrevisionComponent } from './ui/add-prevision/add-prevision.component';
import { EditBacComponent } from './ui/edit-bac/edit-bac.component';
import { PrevisionStockFormComponent } from './ui/prevision-stock-form/prevision-stock-form.component';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ProductsComponent,
    ProductDetailsComponent,
    AddProductComponent,
    BacComponent,
    BacStockComponent,
    EntreSortieComponent,
    DepotComponent,
    DepotDetailsComponent,
    AddBacComponent,
    StockDepotComponent,
    AddDepotComponent,
    PrevisionComponent,
    AddPrevisionComponent,
    EditBacComponent,
    PrevisionStockFormComponent,


  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,

  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
