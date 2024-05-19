package net.lahlalia.stock.mappers;


import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.dtos.HistoryDto;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.HistoryStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryStockMapper {
    HistoryStock toEntity(HistoryDto dto);
    HistoryDto toModel(HistoryStock historyStock);
}
