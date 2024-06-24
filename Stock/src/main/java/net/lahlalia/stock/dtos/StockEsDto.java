package net.lahlalia.stock.dtos;

import lombok.*;

@Getter
@Setter
@Builder // for building objects using .build
@AllArgsConstructor
@NoArgsConstructor
public class StockEsDto {
    private double stockInitial;
    private double entre;
    private double sortie;
    private double stockFinale;
}
