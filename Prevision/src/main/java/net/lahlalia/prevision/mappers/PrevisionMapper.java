package net.lahlalia.prevision.mappers;

import net.lahlalia.prevision.dtos.PrevisionDto;
import net.lahlalia.prevision.entities.Prevision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrevisionMapper {
    Prevision toEntity(PrevisionDto dto);
    PrevisionDto toModel(Prevision prevision);
}
