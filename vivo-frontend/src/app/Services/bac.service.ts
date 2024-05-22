import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {EntreSortie} from "../models/EntreSortie";
import {Bac} from "../models/Bac";
import {Product} from "../models/Product";

@Injectable({
  providedIn: 'root'
})
export class BacService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiBac;

  getBac():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  addBac(bac: Bac| undefined):Observable<Object>{
    return this._http.post(`${this.baseUrl}`, bac);
  }
  deleteBac(idBac: String|undefined):Observable<Object>{
    return this._http.delete(`${this.baseUrl}${idBac}`)
  }

  calculerCreux(idBac: String|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}creux/${idBac}`)
  }
  stockProduit(es:EntreSortie,idBac: String):Observable<Object>{
    return this._http.post(`${this.baseUrl}stock/${idBac}`,es);
  }
  getBacById(idBac:number| undefined):Observable<Bac>{
    return this._http.get<Bac>(`${this.baseUrl}${idBac}`)
  }
  getProductById(idProduct: number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}name/${idProduct}`)
  }


}
