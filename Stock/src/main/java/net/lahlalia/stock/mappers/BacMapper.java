package net.lahlalia.stock.mappers;

import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.entities.Bac;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BacMapper {

    Bac toEntity(BacDto dto);
    BacDto toModel(Bac bac);
}
