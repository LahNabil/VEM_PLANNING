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
import net.lahlalia.stock.repositories.EntreSortieRepository;
import net.lahlalia.stock.repositories.HistoryStockRepository;
import net.lahlalia.stock.restClients.ProductRestClient;
import org.springframework.context.annotation.Lazy;
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
    private final HistoryStockService historyStockService;
    private final EntreSortieRepository entreSortieRepository;
    private final ESMapper esMapper;

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
    public double CalculerStockProduitDepot(String idDepot,String nameProduct){
        if (idDepot == null) {
            throw new IllegalArgumentException("idDepot  must not be null");
        }
        if (nameProduct == null) {
            throw new IllegalArgumentException("nameProduct  must not be null");
        }
        Depot depot = depotRepository.findById(idDepot).get();
        List<Bac> bacList = depot.getBacs();
        List<Bac> bacListFilteredByProductName = bacList.stream()
                .filter(bac-> {
                    String productName = bacService.getProductNameById(bac.getIdProduct());
                    return productName.equals(nameProduct);
                })
                .collect(Collectors.toList());
        double stock = bacListFilteredByProductName.stream()
                .mapToDouble(Bac::getCapacityUsed)
                .sum();

        return stock;

    }
    public List<ESDto> getSortiesParProduitDepot(String idDepot,String nameProduct)throws EntityNotFoundException{
        if(nameProduct == null || idDepot == null){
            return null;
        }
        Depot depot = depotRepository.findById(idDepot).get();
        List<Bac> bacList = depot.getBacs();
        List<Bac> bacListFilteredByProductName = bacList.stream()
                .filter(bac-> {
                    String productName = bacService.getProductNameById(bac.getIdProduct());
                    return productName.equals(nameProduct);
                })
                .collect(Collectors.toList());
        List<EntreSortie> ESList = entreSortieRepository.findAll();

        ESList = ESList.stream()
                .filter(es->bacListFilteredByProductName.stream()
                        .anyMatch(bac->bac.getIdBac().equals(es.getBac().getIdBac())))
                .collect(Collectors.toList());
        List<ESDto> esDtoList = ESList.stream()
                .map(es -> {
                    ESDto esDto = esMapper.toModel(es);
                    if (es.getQuantite() != 0) {
                        if (Boolean.FALSE.equals(es.getTypeES())) {
                            esDto.setQuantite(-es.getQuantite());
                        } else {
                            esDto.setQuantite(es.getQuantite());
                        }
                    }
                    try {
                        esDto.setIdBac(es.getBac().getIdBac());
                        BacDto bacDto = bacService.getBacById(esDto.getIdBac());
                        esDto.setNameProduct(bacService.getProductNameById(bacDto.getIdProduct()));
                    } catch (EntityNotFoundException e) {
                        log.error("Bac not found for Bac ID: " + es.getId());
                    }
                    return esDto;
                })
                .collect(Collectors.toList());
        return esDtoList;
    }
    public double CalculerStockProduitDepotYearMonth(String idDepot, String nameProduct, int year, int month) {

            List<HistoryDto> historyDtoList = historyStockService.getAllHistoryDto();
            List<HistoryDto> filteredHistoryList = historyDtoList.stream()
                    .filter(history -> history.getIdDepot().equals(idDepot) && history.getNameProduct().equals(nameProduct))
                    .collect(Collectors.toList());
            List<ESDto> esDtoList = getSortiesParProduitDepot(idDepot,nameProduct);
            List<StockEsDto> stockEsDtoList = new ArrayList<>();
            double stockInitial = 0.0; // Initialiser avec une valeur par défaut

            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, 1);
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            double entre = 0.0;
            double sortie = 0.0;

            for (int day = 1; day <= maxDay; day++) {


                cal.set(year, month - 1, day);
                Date dateJour = cal.getTime();
                for(ESDto esDto : esDtoList){
                    Calendar esCal = Calendar.getInstance();
                    esCal.setTime(esDto.getDate());
                    if(esCal.get(Calendar.YEAR) == year && esCal.get(Calendar.MONTH) == month - 1
                            && esCal.get(Calendar.DAY_OF_MONTH) == day
                    ){
                        if (esDto.getTypeES() == true){
                            entre += esDto.getQuantite();
                        } else if(esDto.getTypeES() == false){
                            sortie += esDto.getQuantite();
                        }
                    }
                }
                // Rechercher les données historiques pour la date actuelle
                boolean found = false;

                // Rechercher les données historiques pour la date actuelle
                for (HistoryDto history : filteredHistoryList) {
                    Calendar historyCal = Calendar.getInstance();
                    historyCal.setTime(history.getDateJour());
                    if (historyCal.get(Calendar.YEAR) == year &&
                            historyCal.get(Calendar.MONTH) == month - 1 &&
                            historyCal.get(Calendar.DAY_OF_MONTH) == day) {
                        stockInitial = history.getStock(); // Met à jour le stockInitial si une entrée correspondante est trouvée
                        found = true;
                        break; // On peut arrêter la boucle après avoir trouvé la correspondance
                    }
                }


                double stockFinale = stockInitial + entre - sortie;




                // Mettre à jour le stock initial pour le jour suivant
                stockInitial = stockFinale;
                entre = 0;
                sortie = 0;
            }

            return stockInitial;

    }

    public double CalculerStockDepotAllProducts(String idDepot){
        List<BacDto> bacDtos = bacService.getAllBacsForDepot(idDepot);
        double stock = bacDtos.stream().mapToDouble(BacDto::getCapacityUsed).sum();
        return stock;

    }
    public List<Double> calculerStockDepot(){
        List<Depot> depots = depotRepository.findAll();
        List<Double> stockList = new ArrayList<>();
        for(Depot depot: depots){
            double stock = CalculerStockDepotAllProducts(depot.getIdDepot());
            stockList.add(stock);
        }
        return stockList;

    }

    public double CalculerCapacityDepot(String idDepot){
        if(idDepot == null){
            log.error("null values");
            return 0;
        }
        List<BacDto> bacDtos = bacService.getAllBacsForDepot(idDepot);
        double capacity = bacDtos.stream().mapToDouble(BacDto::getCapacity).sum();
        return capacity;

    }
    public List<Double> CalculerCapacites(){
        List<Depot> depots = depotRepository.findAll();
        List<Double> capacities = new ArrayList<>();
        for(Depot depot: depots){
            double capacity = CalculerCapacityDepot(depot.getIdDepot());
            capacities.add(capacity);
        }
        return capacities;
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
    //Executer chaque 24Heures
    @Scheduled(fixedRate = 86400000)
    public void saveDailyStock() {
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
    public String getCity(String idDepot)throws DepotNotFoundException{
        if(idDepot == null){
            log.error("value is null");
            return null;
        }
        Depot depot = depotRepository.findById(idDepot).get();
        String city = depot.getZone();
        return city;
    }







}
