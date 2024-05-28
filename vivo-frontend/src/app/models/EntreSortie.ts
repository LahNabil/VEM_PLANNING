import {Bac} from "./Bac";

export class EntreSortie{
  id: number|undefined;
  quantite: number|undefined;
  date: Date|undefined;
  typeES: boolean|undefined;
  business:any;
  idBac: number|undefined;
  bac?: Bac;
  nameProduct: string| undefined;

}
