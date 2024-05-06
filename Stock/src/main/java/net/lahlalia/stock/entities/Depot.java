package net.lahlalia.stock.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Depot {

    @Id
    private String idDepot;
    private String nameDepot;
    private String zone;
    private String area;
    private double capaciteDepot;



}
