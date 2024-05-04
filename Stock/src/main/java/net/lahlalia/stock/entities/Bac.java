package net.lahlalia.stock.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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

}
