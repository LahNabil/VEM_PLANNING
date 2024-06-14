import {Bac} from "./Bac";
import {BacDto} from "./BacDto";

export class Depot{
  idDepot !: string;
  nameDepot: String|undefined;
  zone: String|undefined;
  area : any|undefined;
  bacDtos: BacDto[]|undefined;
  stock: number|undefined;


}
