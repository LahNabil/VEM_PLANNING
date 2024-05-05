package net.lahlalia.stock.entities;

import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.stock.dtos.Product;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bac {
    @Id
    private String idBac;
    private double capacity;
    private double totalImpom;
    private boolean status;
    private Date dateOuverture;
    private double capacityUsed;
    private Long idProduct;

}
