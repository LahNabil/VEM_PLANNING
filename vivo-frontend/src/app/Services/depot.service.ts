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
  addDepot(depot: Depot| undefined):Observable<Object>{
    return this._http.post(`${this.baseUrl}`, depot);
  }
  editDepot(idDepot:number|undefined,depot: Depot):Observable<Object>{
    return this._http.put(`${this.baseUrl}${idDepot}`, depot)
  }

  calculerStock(idDepot: String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStock/${idDepot}`)
  }
  calculerStocksProduits(idDepot:String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}calculerStocksProduits/${idDepot}`)
  }

}
