package net.lahlalia.produit.dtos;

import lombok.*;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.TypeProduit;

@Getter
@Setter
@Builder // for building objects using .build
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
private Long idProduit;
private String name;
private String status;
private TypeProduit type;
private RegimeDto regime;


}
