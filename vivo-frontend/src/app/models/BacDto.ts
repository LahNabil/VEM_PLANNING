import {Product} from "./Product";

export class BacDto{
  idBac: String|undefined;
  capacity: number|undefined;
  totalImpom: number|undefined;
  status: boolean|undefined;
  dateOuverture: Date|undefined;
  capacityUsed: number|undefined;
  idProduct: number|undefined;
  product?: Product;

}
