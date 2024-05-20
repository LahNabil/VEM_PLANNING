package net.lahlalia.stock.entities;

import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.stock.enums.Area;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToMany(mappedBy = "depot")
    private List<Bac> bacs;

    @OneToMany(mappedBy = "depot")
    private List<HistoryStock> historyStocks;





}
