package net.lahlalia.prevision.entities;


import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.prevision.enums.Business;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Prevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrevision;
    private double foreCaste;
    private double vReel;
    private int mois;
    private int annee;
    @Enumerated(EnumType.STRING)
    private Business business;
    @OneToMany(mappedBy = "prevision")
    private List<BacItem> bacItems;




}
