package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.*;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.entities.HistoryStock;
import net.lahlalia.stock.exceptions.DepotNotFoundException;
import net.lahlalia.stock.mappers.*;
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
    private final MapperDepot depotMapper;
    private final BacRepository bacRepository;
    private final BacService bacService;
    private final MapperBac bacMapper;
    private final ProductRestClient productRestClient;
    private final HistoryStockRepository historyStockRepository;
    private final MapperHistoryStock historyStockMapper;

    public DepotDTO saveDepot(DepotDTO dto){
        return depotMapper.toModel(
                depotRepository.save(
                        depotMapper.toEntity(dto)
                )
        );

    }
    public List<BacDto> findBacsByProductAndZone(String nameProduct, String zoneDepot) {
        List<DepotDTO> allDepots = geAllDepots();
        List<BacDto> result = new ArrayList<>();

        // Filter depots by zone
        List<DepotDTO> filteredDepots = allDepots.stream()
                .filter(depot -> depot.getZone().equalsIgnoreCase(zoneDepot))
                .collect(Collectors.toList());

        // Filter Bacs by product name within the filtered depots
        for (DepotDTO depot : filteredDepots) {
            String depotId = depot.getIdDepot(); // Get the ID of the current depot
            List<BacDto> filteredBacs = depot.getBacDtos().stream()
                    .filter(bac -> {
                        String productName = bacService.getProductNameById(bac.getIdProduct());
                        return productName != null && productName.equalsIgnoreCase(nameProduct);
                    })
                    .peek(bac -> bac.setIdDepot(depotId)) // Set the idDepot field of each BacDto
                    .collect(Collectors.toList());
            result.addAll(filteredBacs);
        }


        return result;
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
    @Scheduled(fixedRate = 86400000)
    public void saveDailyStock() {
//        List<Depot> depots = depotRepository.findAll();
//        for(Depot depot : depots){
//            List<StockProduitDto> stockProduitDtos = calculerStocksProduitsDansDepot(depot);
//
//        }
        List<DepotDTO> depotDTOS = geAllDepots();
        for (DepotDTO depotDTO : depotDTOS) {
            List<StockProduitDto> stockProduitDtos = calculerStocksProduitsDansDepot(depotDTO);
            if (stockProduitDtos != null) {
                for (StockProduitDto stockProduitDto : stockProduitDtos) {
                    HistoryStock historyStock = new HistoryStock().builder()
                            .dateJour(new Date())
                            .stock(stockProduitDto.getQuantite())
                            .depot(depotMapper.toEntity(depotDTO))
                            .nameProduct(stockProduitDto.getNameProduit())
                            .build();
                    historyStockRepository.save(historyStock);
                    HistoryDto historyDto = historyStockMapper.toModel(historyStock);

                }
            } else {
                log.error("stockProduitDtos is null for depot: {}", depotDTO.getIdDepot());
                // Handle the null case accordingly
            }
        }
    }

    //stockSecurité = consomations journaliére x 3jrs
    //consomation journalière = myenne de vente de l'année precedente
//    public double calculerStockSecurite(String idDepot)throws DepotNotFoundException {
    public double calculerStockSecurite(double quantity)throws DepotNotFoundException{
//        if(idDepot == null){
//            log.error("value is null");
//            return 0;
//        }
//        Depot depot = depotRepository.findById(idDepot)
//                .orElseThrow(() -> new DepotNotFoundException("Depot with ID " + idDepot + " not found"));
//        DepotDTO depotDTO = depotMapper.toModel(depot);
        // logique de calcule de stock de securité
        double value = 1130;
        return value;
    }
//    public List<StockProduitDto> calculerStockSecuritePourListe(List<StockProduitDto> stockProduits) {
//        for (StockProduitDto stockProduitDto : stockProduits) {
//            double stockSecurite = calculerStockSecurite(stockProduitDto.getQuantite());
//            stockProduitDto.setStockSecurite(stockSecurite);
//        }
//        return stockProduits;
//    }

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
            stocksProduits.add(new StockProduitDto(productName, stockProduit,calculerStockSecurite(stockProduit)));
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
