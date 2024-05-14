package net.lahlalia.stock.mappers;

import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.entities.EntreSortie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ESMapper {
    EntreSortie toEntity(ESDto dto);
    ESDto toModel(EntreSortie es);
}
