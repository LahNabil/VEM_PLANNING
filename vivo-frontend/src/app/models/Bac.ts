import {Regime} from "./Regime";
import {Product} from "./Product";

export class Bac{
  idBac: String|undefined;
  capacity: number|undefined;
  totalImpom: number|undefined;
  status: boolean|undefined;
  dateOuverture: Date|undefined;
  capacityUsed: number|undefined;
  idProduct: number|undefined;
  product?: Product;
  creux: number|undefined;


}

