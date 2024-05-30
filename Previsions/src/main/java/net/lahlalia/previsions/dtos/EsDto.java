package net.lahlalia.previsions.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder // for building objects using .build
@AllArgsConstructor
@NoArgsConstructor
public class EsDto {
    private Long id;
    private double quantite;
    private Date date;
    private Boolean typeES;
    private String business;
    private String  idBac;
    private String nameProduct;
}
