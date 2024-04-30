import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {Product} from "../models/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiProduct;

  getProducts():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }

  getProductById(idProduit: number | undefined):Observable<Product>{
    return this._http.get<Product>(`${this.baseUrl}${idProduit}`)
  }
  deleteProduct(idProduit:number|undefined):Observable<Object>{
    return this._http.delete(`${this.baseUrl}${idProduit}`)
  }
}
