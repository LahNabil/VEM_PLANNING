import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {EntreSortie} from "../models/EntreSortie";
import {Bac} from "../models/Bac";

@Injectable({
  providedIn: 'root'
})
export class BacService {

  constructor(private _http: HttpClient) { }
  private baseUrl = environment.apiBac;

  getBac():Observable<any>{
    return this._http.get(`${this.baseUrl}`)
  }
  entrerProduit(es:EntreSortie,idBac: String):Observable<Object>{
    return this._http.post(`${this.baseUrl}entrer/${idBac}`,es);
  }
}
