package net.lahlalia.produit.dtos;

import lombok.*;
import net.lahlalia.produit.enums.RegimeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegimeDto {
    private Long idRegime;
    private RegimeType regime;
}
