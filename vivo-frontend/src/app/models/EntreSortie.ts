import {Bac} from "./Bac";

export class EntreSortie{
  id: number|undefined;
  quantite: number|undefined;
  date: Date|undefined;
  typeES: boolean|undefined;
  idBac: number|undefined;
  bac?: Bac;
  productName: String| undefined;

}
