package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.mappers.ESMapper;
import net.lahlalia.stock.mappers.MapperEs;
import net.lahlalia.stock.repositories.DepotRepository;
import net.lahlalia.stock.repositories.EntreSortieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
