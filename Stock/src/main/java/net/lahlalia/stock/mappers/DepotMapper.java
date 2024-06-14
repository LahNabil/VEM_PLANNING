package net.lahlalia.stock.mappers;

import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepotMapper {

    Depot toEntity(DepotDTO dto);
    DepotDTO toModel(Depot depot);
}
