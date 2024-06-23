import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {Product} from "../models/Product";
import {Depot} from "../models/Depot";

@Injectable({
  providedIn: 'root'
})
export class DepotService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiDepot;

  getDepots():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  getDepotById(idDepot: String | undefined):Observable<Depot>{
    return this._http.get<Depot>(`${this.baseUrl}${idDepot}`)
  }
  deleteDepotById(idDepot: String | undefined):Observable<Object>{
    return this._http.delete(`${this.baseUrl}${idDepot}`)
  }

  addDepot(depot: Depot| undefined):Observable<Object>{
    return this._http.post(`${this.baseUrl}`, depot);
  }
  editDepot(idDepot:number|undefined,depot: Depot):Observable<Object>{
    return this._http.put(`${this.baseUrl}${idDepot}`, depot)
  }

  calculerStock(idDepot: String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStock/${idDepot}`)
  }
  calculerCapacity(idDepot:String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStock/${idDepot}`)
  }
  calculerStocksProduits(idDepot:String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}capacitydepot/${idDepot}`)
  }
  calculerStockDepotProduitDate(idDepot:String|undefined, nameProduct:String|undefined, year:number|undefined, month:number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStockProduitDepotDate/${idDepot}/${nameProduct}/${year}/${month}`)
  }
  calculerStockDepotProduit(idDepot:String|undefined, nameProduct:String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStockProduit/${idDepot}/${nameProduct}`)
  }

  calculerCapacities():Observable<any>{
    return this._http.get(`${this.baseUrl}capacities`)
  }
  calculerStocks():Observable<any>{
    return this._http.get(`${this.baseUrl}stocks`)
  }
  calculerCapacityDepot(idDepot:String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}capacitydepot/${idDepot}`)
  }


}
