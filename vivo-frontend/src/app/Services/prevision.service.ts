import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {Bac} from "../models/Bac";
import {Prevision} from "../models/Prevision";

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
  calculerVreel(idPrevision : number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}vreel/${idPrevision}`)
  }
  calculerABS(idPrevision: number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}ABS/${idPrevision}`)
  }
}
