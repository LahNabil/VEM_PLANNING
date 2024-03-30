package net.lahlalia.produit.mappers;

import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.dtos.RegimeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegimeMapper {

    Regime toEntity(RegimeDto dto);
    RegimeDto toModel(Regime regime);


}
