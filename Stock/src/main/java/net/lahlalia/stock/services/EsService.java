package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.*;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.entities.HistoryStock;
import net.lahlalia.stock.mappers.ESMapper;
import net.lahlalia.stock.mappers.MapperEs;
import net.lahlalia.stock.repositories.DepotRepository;
import net.lahlalia.stock.repositories.EntreSortieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EsService {
    private final EntreSortieRepository entreSortieRepository;
    private final MapperEs esMapper;
    private final BacService bacService;
    private final DepotService depotService;
    private final DepotRepository depotRepository;
    private final HistoryStockService historyStockService;

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
    public List<ESDto> getSortiesParProduitDepotDate(String idDepot, String nameProduct, int year, int month) throws EntityNotFoundException {
        if (idDepot == null) {
            throw new IllegalArgumentException("idDepot must not be null");
        }
        if (nameProduct == null) {
            throw new IllegalArgumentException("nameProduct must not be null");
        }

        // Correct the month value (0-based index)
        month = month - 1;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();
        log.info("Start Date: {}", startDate);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();
        log.info("End Date: {}", endDate);

        Depot depot = depotRepository.findById(idDepot).orElseThrow(() -> new EntityNotFoundException("Depot not found"));
        List<Bac> bacList = depot.getBacs();
        List<Bac> bacListFilteredByProductName = bacList.stream()
                .filter(bac -> {
                    String productName = bacService.getProductNameById(bac.getIdProduct());
                    return productName.equals(nameProduct);
                })
                .collect(Collectors.toList());

        List<EntreSortie> ESList = entreSortieRepository.findAll();

        ESList = ESList.stream()
                .filter(es -> {
                    boolean matchesBac = bacListFilteredByProductName.stream()
                            .anyMatch(bac -> bac.getIdBac().equals(es.getBac().getIdBac()));
                    boolean matchesDate = !es.getDate().before(startDate) && !es.getDate().after(endDate);
                    return matchesBac && matchesDate;
                })
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

    public List<ESDto> getAllES(){
        List<EntreSortie> ESList = entreSortieRepository.findAll();
        List<ESDto> esDtoList = new ArrayList<>();
        for(EntreSortie es : ESList){
            ESDto esDto = esMapper.toModel(es);
            if (es.getQuantite() != 0) {
                if (Boolean.FALSE.equals(es.getTypeES())) {
                    esDto.setQuantite(-es.getQuantite());
                } else {
                    esDto.setQuantite(es.getQuantite());
                }
            }
            try{
                esDto.setIdBac(es.getBac().getIdBac());
                BacDto bacDto = bacService.getBacById(esDto.getIdBac());
                esDto.setNameProduct(bacService.getProductNameById(bacDto.getIdProduct()));

            }catch (EntityNotFoundException e) {

                log.error("Bac not found for Bac ID: " + es.getId());
            }
            esDtoList.add(esDto);
        }
        return esDtoList;
//       return entreSortieRepository.findAll().stream().map(esMapper::toModel).toList();
    }

//

    public double GetEntreebymonth(){
        List<EntreSortie> ESList = entreSortieRepository.findAll();
        double totalQuantity = 0;
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH);
        int currentYear = now.get(Calendar.YEAR);
        for(EntreSortie es : ESList){
            ESDto esDto = esMapper.toModel(es);
            Calendar cal = Calendar.getInstance();
            cal.setTime(esDto.getDate());
            int esMonth = cal.get(Calendar.MONTH);
            int esYear = cal.get(Calendar.YEAR);
            if (esMonth == currentMonth && esYear == currentYear && esDto.getTypeES() ==true ) {
                totalQuantity += esDto.getQuantite();
            }
        }
        return totalQuantity;
    }
//    public StockEsDto CreateStockEsDto(String idDepot,String nameProduct,int year, int month, int jour) {
//        List<HistoryDto> historyDtoList = historyStockService.getAllHistoryDto();
//        List<EntreSortie> ESList = entreSortieRepository.findAll();
//        Depot depot = depotRepository.findById(idDepot).get();
//        List<Bac> bacList = depot.getBacs();
//
//        List<Bac> bacListFilteredByProductName = bacList.stream()
//                .filter(bac -> {
//                    String productName = bacService.getProductNameById(bac.getIdProduct());
//                    return productName.equals(nameProduct);
//                })
//                .collect(Collectors.toList());
//
//        month = month - 1;
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.MONTH, month);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        Date startDate = cal.getTime();
//        log.info("Start Date: {}", startDate);
//
//        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date endDate = cal.getTime();
//        log.info("End Date: {}", endDate);
//        List<HistoryDto> filteredHistory = historyDtoList.stream()
//                .filter(history -> {
//                    boolean idDepotMatch = history.getIdDepot().equals(idDepot);
//                    boolean nameProductMatch = history.getNameProduct().equals(nameProduct);
//                    Date historyDate = history.getDateJour();
//                    boolean dateMatch = !historyDate.before(startDate) && !historyDate.after(endDate);
//                    log.info("History Record - ID: {}, Product: {}, Date: {}, ID Match: {}, Product Match: {}, Date Match: {}",
//                            history.getIdDepot(), history.getNameProduct(), historyDate, idDepotMatch, nameProductMatch, dateMatch);
//                    return idDepotMatch && nameProductMatch && dateMatch;
//                })
//                .collect(Collectors.toList());
//        ESList = ESList.stream()
//                .filter(es -> {
//                    boolean matchesBac = bacListFilteredByProductName.stream()
//                            .anyMatch(bac -> bac.getIdBac().equals(es.getBac().getIdBac()));
//                    boolean matchesDate = !es.getDate().before(startDate) && !es.getDate().after(endDate);
//                    return matchesBac && matchesDate;
//                })
//                .collect(Collectors.toList());
//    }


//        List<ESDto> filteredES = entreSortieList.stream()
//                .filter(es -> {
//                    boolean idDepotMatch = es.getIdBac().equals(idDepot);
//                    String productName = bacService.getProductNameById(bac.getIdProduct());
//                    boolean nameProductMatch = es.getNameProduct().equals(nameProduct);
//                    Date esDate = es.getDate();
//                    boolean dateMatch = !esDate.before(startDate) && !esDate.after(endDate);
//                    log.info("ES Record - ID: {}, Product: {}, Date: {}, ID Match: {}, Product Match: {}, Date Match: {}",
//                            es.getIdDepot(), es.getNameProduct(), esDate, idDepotMatch, nameProductMatch, dateMatch);
//                    return idDepotMatch && nameProductMatch && dateMatch;
//                })
//                .collect(Collectors.toList());




    public double getSortiebymonth(){
        List<EntreSortie> ESList = entreSortieRepository.findAll();
        double totalQuantity = 0;
        Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH);
        int currentYear = now.get(Calendar.YEAR);

        List<ESDto> esDtoList = new ArrayList<>();
        for(EntreSortie es : ESList){
            ESDto esDto = esMapper.toModel(es);
            Calendar cal = Calendar.getInstance();
            cal.setTime(esDto.getDate());
            int esMonth = cal.get(Calendar.MONTH);
            int esYear = cal.get(Calendar.YEAR);
            if (esMonth == currentMonth && esYear == currentYear && esDto.getTypeES() ==false ) {
                totalQuantity += esDto.getQuantite();
            }
        }
        return totalQuantity;
    }
    public List<StockEsDto> generateMonthlyStockReport(String idDepot,String nameProduct,int year, int month) {

        List<HistoryDto> historyDtoList = historyStockService.getAllHistoryDto();
        List<HistoryDto> filteredHistoryList = historyDtoList.stream()
                .filter(history -> history.getIdDepot().equals(idDepot) && history.getNameProduct().equals(nameProduct))
                .collect(Collectors.toList());
        List<ESDto> esDtoList = getSortiesParProduitDepot(idDepot,nameProduct);
        List<StockEsDto> stockEsDtoList = new ArrayList<>();
        double stockInitial = 1000.0; // Initialiser avec une valeur par défaut

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

            StockEsDto stockDto = StockEsDto.builder()
                    .dateJour(dateJour)
                    .stockInitial(stockInitial)
                    .entre(entre)
                    .sortie(sortie)
                    .stockFinale(stockFinale)
                    .build();

            stockEsDtoList.add(stockDto);

            // Mettre à jour le stock initial pour le jour suivant
            stockInitial = stockFinale;
            entre = 0;
            sortie = 0;
        }

        return stockEsDtoList;
    }
    public double CalculerStockProduitDepotYearMonth(String idDepot, String nameProduct, int year, int month) {
        List<StockEsDto> stockEsDtoList = generateMonthlyStockReport(idDepot,nameProduct,year,month);
        double stock;
        if (!stockEsDtoList.isEmpty()) {
            StockEsDto lastStockEsDto = stockEsDtoList.get(stockEsDtoList.size() - 1);
            stock = lastStockEsDto.getStockFinale();  // Assuming getStock() method returns the stock value
            return stock;
        }else {
            return 0;
        }

    }

}

