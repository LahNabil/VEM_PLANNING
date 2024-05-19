package net.lahlalia.stock.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.HistoryDto;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.HistoryStock;
import net.lahlalia.stock.mappers.DepotMapper;
import net.lahlalia.stock.mappers.HistoryStockMapper;
import net.lahlalia.stock.repositories.HistoryStockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryStockService {

    private final HistoryStockRepository historyStockRepository;
    private final HistoryStockMapper historyStockMapper;
    private final DepotMapper depotMapper;

    public List<HistoryDto> getAllHistoryDto() {
        List<HistoryStock> histories = historyStockRepository.findAll();
        return histories.stream()
                .map(history -> {
                    HistoryDto historyDto = historyStockMapper.toModel(history);
                    historyDto.setDepotDTO(depotMapper.toModel(history.getDepot()));
                    historyDto.setIdDepot(history.getDepot().getIdDepot());// Assuming History entity has a 'depot' field
                    return historyDto;
                })
                .collect(Collectors.toList());
    }
}
