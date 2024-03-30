package net.lahlalia.produit.entities;

import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.produit.enums.RegimeType;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Regime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegime;
    @Enumerated(EnumType.STRING)
    private RegimeType regime;
    @OneToMany(mappedBy = "regime")
    private List<Produit> produits;


}
