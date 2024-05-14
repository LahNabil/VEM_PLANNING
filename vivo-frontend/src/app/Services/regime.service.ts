import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegimeService {

  constructor(private _http: HttpClient) { }
  private apiReg = environment.apiRegime;
  getRegimes():Observable<any>{
    return this._http.get(`${this.apiReg}`)
  }
}
