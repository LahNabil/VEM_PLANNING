package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.dtos.StockProduitDto;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.mappers.BacMapper;
import net.lahlalia.stock.mappers.DepotMapper;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.repositories.DepotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepotService {
    private final DepotRepository depotRepository;
    private final DepotMapper depotMapper;
    private final BacRepository bacRepository;
    private final BacService bacService;
    private final BacMapper bacMapper;

    public DepotDTO saveDepot(DepotDTO dto){
        return depotMapper.toModel(
                depotRepository.save(
                        depotMapper.toEntity(dto)
                )
        );

    }
    public DepotDTO editDepot(String idDepot, DepotDTO depotDTO)throws EntityNotFoundException{
        Depot existingdepot = depotRepository.findById(idDepot)
                .orElseThrow(() -> new EntityNotFoundException("Depot with ID " + idDepot + " not found"));
        existingdepot.setNameDepot(depotDTO.getNameDepot());
        existingdepot.setZone(depotDTO.getZone());
        existingdepot.setArea(depotDTO.getArea());

        Depot updatedDepot = depotRepository.save(existingdepot);

        return depotMapper.toModel(updatedDepot);

    }

    public double CalculerStock(String idDepot){
        List<BacDto> bacDtos = bacService.getAllBacsForDepot(idDepot);
        double stock = bacDtos.stream().mapToDouble(BacDto::getCapacityUsed).sum();
        return stock;

    }
    public DepotDTO getDepotById(String idDepot )throws EntityNotFoundException{
        if(idDepot == null){
            log.error("id Depot is null");
            return null;
        }
        Depot depot = depotRepository.findById(idDepot).get();
        DepotDTO depotDTO = depotMapper.toModel(depot);
        List<BacDto> bacDtos = bacService.getAllBacsForDepot(idDepot);
        depotDTO.setBacDtos(bacDtos);
        return depotDTO;


    }
    public List<StockProduitDto> calculerStocksProduitsDansDepot(DepotDTO depotDTO) {
        List<BacDto> bacDtos = depotDTO.getBacDtos();

        // Collecter les ID de produits distincts présents dans les Bacs
        Set<String> distinctProductNames = bacDtos.stream()
                .map(bac -> bacService.getProductNameById(bac.getIdProduct()))
                .collect(Collectors.toSet());

        // Pour chaque produit distinct, calculer le stock
        List<StockProduitDto> stocksProduits = new ArrayList<>();
        for (String productName : distinctProductNames) {
            double stockProduit = bacDtos.stream()
                    .filter(bac -> bacService.getProductNameById(bac.getIdProduct()).equals(productName))
                    .mapToDouble(BacDto::getCapacityUsed)
                    .sum();
            stocksProduits.add(new StockProduitDto(productName, stockProduit));
        }

        return stocksProduits;
    }


    public List<DepotDTO> geAllDepots(){
        List<Depot> depots = depotRepository.findAll();
        List<DepotDTO> depotDTOS = new ArrayList<>();
//        List<BacDto> bacDtos = new ArrayList<>();

        for(Depot depot : depots){
            List<BacDto> bacDtos = bacService.getAllBacsForDepot(depot.getIdDepot());
            DepotDTO depotDTO = depotMapper.toModel(depot);
            depotDTO.setBacDtos(bacDtos);
            depotDTOS.add(depotDTO);
        }
        return depotDTOS;

    }







}
