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
}
