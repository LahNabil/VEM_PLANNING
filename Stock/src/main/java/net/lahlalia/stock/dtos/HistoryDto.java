package net.lahlalia.stock.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lahlalia.stock.entities.Depot;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

    private Long idHistory;

    private Date dateJour;
    private String nameProduit;
    private double stock;
    @ManyToOne
    private DepotDTO depotDTO;
}
