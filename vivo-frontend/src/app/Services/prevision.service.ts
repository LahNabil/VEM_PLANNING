import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PrevisionService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiPrevision;

  getPrevision():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  calculerVreel(idPrevision : number|undefined):Observable<any>{
    return this._http.get(`${this.baseUrl}vreel/${idPrevision}`)
  }
}
