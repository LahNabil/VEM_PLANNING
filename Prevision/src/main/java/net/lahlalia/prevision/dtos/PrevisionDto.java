package net.lahlalia.prevision.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.enums.Business;

import java.util.List;

public class PrevisionDto {
    private Long idPrevision;
    private double foreCaste;
    private double vReel;
    private int mois;
    private int annee;
    @Enumerated(EnumType.STRING)
    private Business business;
    @OneToMany
    private List<BacItem> bacItems;
}
