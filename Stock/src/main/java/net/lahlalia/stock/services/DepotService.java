package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.*;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.entities.HistoryStock;
import net.lahlalia.stock.mappers.BacMapper;
import net.lahlalia.stock.mappers.DepotMapper;
import net.lahlalia.stock.mappers.HistoryStockMapper;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.repositories.DepotRepository;
import net.lahlalia.stock.repositories.HistoryStockRepository;
import net.lahlalia.stock.restClients.ProductRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private final ProductRestClient productRestClient;
    private final HistoryStockRepository historyStockRepository;
    private final HistoryStockMapper historyStockMapper;

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

    public boolean deleteDepotById(String idDepot)throws EntityNotFoundException{
        DepotDTO dto = getDepotById(idDepot);
        if( dto != null){
            depotRepository.deleteById(idDepot);
            return true;
        } else {
            return false;
        }

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
     // Exécuter à minuit tous les jours
    @Scheduled(fixedRate = 30000)
    public void saveDailyStock() {
        List<DepotDTO> depotDTOS = geAllDepots();
        for (DepotDTO depotDTO : depotDTOS) {
            List<StockProduitDto> stockProduitDtos = calculerStocksProduitsDansDepot(depotDTO);
            if (stockProduitDtos != null) { // Check for null before iterating
                for (StockProduitDto stockProduitDto : stockProduitDtos) {
                    HistoryDto historyDto = new HistoryDto().builder()
                            .dateJour(new Date())
                            .stock(stockProduitDto.getQuantite())
                            .depotDTO(depotDTO)
                            .nameProduct(stockProduitDto.getNameProduit())
                            .build();
                    HistoryStock historyStock = historyStockMapper.toEntity(historyDto);
                    historyStockRepository.save(historyStock);
                }
            } else {
                log.error("stockProduitDtos is null for depot: {}", depotDTO.getIdDepot());
                // Handle the null case accordingly
            }
        }
    }

    public List<StockProduitDto> calculerStocksProduitsDansDepot(DepotDTO depotDTO) {
        List<BacDto> bacDtos = depotDTO.getBacDtos();
        if (bacDtos == null) {
            log.error("BacDtos is null for depot: {}", depotDTO.getIdDepot());
            return Collections.emptyList();// Return an empty list or handle it according to your use case
        }

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
