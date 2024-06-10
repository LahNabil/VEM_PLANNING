import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {Bac} from "../models/Bac";
import {Prevision} from "../models/Prevision";
import {Product} from "../models/Product";

@Injectable({
  providedIn: 'root'
})
export class PrevisionService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiPrevision;

  getPrevision():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  addPrevision(prevision: Prevision|undefined):Observable<Object>{
    return this._http.post(`${this.baseUrl}`,prevision);
  }

  addBac(bac: Bac| undefined):Observable<Object>{
    return this._http.post(`${this.baseUrl}`, bac);
  }
  editPrevision(idPrevision:number | undefined, prevision: Prevision):Observable<Object>{
    return this._http.put(`${this.baseUrl}${idPrevision}`, prevision);
  }
  deletePrevisionById(idPrevision: number | undefined):Observable<Object>{
    return this._http.delete(`${this.baseUrl}${idPrevision}`)
  }
  calculerVreel(idPrevision : number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}vreel/${idPrevision}`)
  }
  calculerABS(idPrevision: number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}ABS/${idPrevision}`)
  }
  calculerAccuracy(idPrevision:number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}accuracy/${idPrevision}`)
  }
  isStockSuffisant(idPrevision:number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}suffisant/${idPrevision}`)
  }
}
