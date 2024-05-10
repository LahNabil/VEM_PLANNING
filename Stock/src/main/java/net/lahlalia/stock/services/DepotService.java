package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.mappers.DepotMapper;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.repositories.DepotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepotService {
    private final DepotRepository depotRepository;
    private final DepotMapper depotMapper;
    private final BacRepository bacRepository;
    private final BacService bacService;


    public DepotDTO saveDepot(DepotDTO depotDto)throws EntityNotFoundException{
        if(depotDto == null){
            log.error("Depot is null");
            return null;
        }
        Depot depot = depotMapper.toEntity(depotDto);

        List<Bac> bacs = new ArrayList<>();
        for(Bac bac : depotDto.getBacs()){
            bacs.add(bac);

        }
        depot.setBacs(bacs);
        depot = depotRepository.save(depot);
        return depotMapper.toModel(depot);

    }
    public List<DepotDTO> getDepots(){
//        return depotRepository.findAll().stream().map(depotMapper::toModel).toList();

        List<Depot> depots = depotRepository.findAll();
        List<DepotDTO> depotDTOS = new ArrayList<>();
        for(Depot depot : depots){
            DepotDTO depotDTO = depotMapper.toModel(depot);
            depotDTO.setBacs(depot.getBacs());
            depotDTOS.add(depotDTO);

        }
        return depotDTOS;
    }

}
