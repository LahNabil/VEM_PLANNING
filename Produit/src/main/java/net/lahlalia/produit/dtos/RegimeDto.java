package net.lahlalia.produit.dtos;

import lombok.*;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.enums.RegimeType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegimeDto {
    private Long idRegime;
    private RegimeType regime;

}
