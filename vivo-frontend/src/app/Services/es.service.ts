import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ESService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiES;

  getEs():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  getSortiesParProduitDepot(idDepot :string|undefined, nameProduct:string|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}${idDepot}/${nameProduct}`)
  }
  getEntreeBymonth():Observable<any>{
    return this._http.get(`${this.baseUrl}entreeByMonth`)
  }
  getSortieByMonth():Observable<any>{
    return this._http.get(`${this.baseUrl}sortieByMonth`)
  }
  getSortiesParProduitDepotDate(idDepot:string|undefined,nameProduct:string|undefined,year:number|undefined,month:number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}${idDepot}/${nameProduct}/${year}/${month}`)
  }
  generateMonthlyStockReport(idDepot: string|undefined,nameProduct: string|undefined,year: number|undefined,month:number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}montly/${idDepot}/${nameProduct}/${year}/${month}`)
  }


}
