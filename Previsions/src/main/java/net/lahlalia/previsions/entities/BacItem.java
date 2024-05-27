package net.lahlalia.previsions.entities;



import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BacItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idBac;
    private Long idProduct;
    private String idDepot;
    private String nameProduct;
    @ManyToOne
    private Prevision prevision;


}
