package net.lahlalia.produit.entities;

import jakarta.persistence.*;
import lombok.*;
import net.lahlalia.produit.enums.TypeProduit;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit;
    private String name;
    private String status;
    @Enumerated(EnumType.STRING)
    private TypeProduit type;
    @ManyToOne
    private Regime regime;

}
