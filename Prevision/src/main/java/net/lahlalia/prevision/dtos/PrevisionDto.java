package net.lahlalia.prevision.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.enums.Business;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrevisionDto {
    private Long idPrevision;
    private double foreCaste;
    private double vReel;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    @Enumerated(EnumType.STRING)
    private Business business;
    private Long idProduct;
    private String nameProduct;
}