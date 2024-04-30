import {Regime} from "./Regime";

export class Product{
  idProduit: number|undefined;
  name: String|undefined;
  status: String|undefined;
  type: any|undefined;
  regime!: Regime;

}
