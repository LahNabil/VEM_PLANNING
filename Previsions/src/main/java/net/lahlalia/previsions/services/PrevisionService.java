package net.lahlalia.previsions.services;




import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.EsDto;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import net.lahlalia.previsions.mappers.BacMapper;
import net.lahlalia.previsions.mappers.MapperBac;
import net.lahlalia.previsions.mappers.MapperPrevision;
import net.lahlalia.previsions.mappers.PrevisionMapper;
import net.lahlalia.previsions.repositories.BacItemRepository;
import net.lahlalia.previsions.repositories.PrevisionRepository;
import net.lahlalia.previsions.restclients.StockRestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrevisionService {

    private final PrevisionRepository previsionRepository;
    private final StockRestClient stockRestClient;
    private final MapperPrevision mapperPrevision;
    private final MapperBac mapperBac;
    private final BacItemRepository bacItemRepository;




    public PrevisionDto savePrevision(PrevisionDto previsionDto)throws EntityNotFoundException{
        if(previsionDto == null){
            log.error(" value is null");
            return null;
        }

        Prevision prevision = mapperPrevision.convertToModel(previsionDto);

//        List<BacItem> bacs = stockRestClient.getBacsByProductAndZone(previsionDto.getNameProduct(),previsionDto.getSupplyEnveloppe()).stream().map(mapperBac::convertToModel).toList();
        List<Bac> bacs = stockRestClient.getBacsByProductAndZone(previsionDto.getNameProduct(),previsionDto.getSupplyEnveloppe());
        Prevision savedPrevision = previsionRepository.save(prevision);
        List<BacItem> bacItems = new ArrayList<>();
        bacs.forEach(b->{
            BacItem bacItem = BacItem.builder()
                    .idBac(b.getIdBac())
                    .idDepot(b.getIdDepot())
                    .idProduct(b.getIdProduct())
                    .prevision(prevision)
                    .build();
            bacItemRepository.save(bacItem);
            bacItems.add(bacItem);
        });
        savedPrevision.setBacItems(bacItems);
        return mapperPrevision.convertToDto(savedPrevision);


    }
    public List<PrevisionDto>getAllPrevision(){
        return previsionRepository.findAll().stream().map(mapperPrevision::convertToDto).toList();
    }


    public PrevisionDto getPrevisionById(Long idPrevision) throws EntityNotFoundException{
        if(idPrevision == null){
            log.error("Id is null");
            return null;
        }
        Prevision prevision = previsionRepository.findById(idPrevision).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);
        return previsionDto;
    }
    public List<BacItem> getBacsByProdZonePrevision(Long idPrevision) {
        Prevision prevision = previsionRepository.findById(idPrevision)
                .orElseThrow(() -> new EntityNotFoundException("Prevision Not found"));
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        // Check for null values
        if (previsionDto.getNameProduct() == null || previsionDto.getSupplyEnveloppe() == null) {
            throw new IllegalArgumentException("NameProduct or SupplyEnveloppe is null");
        }

        String nameProduct = previsionDto.getNameProduct();
        String zoneDepot = previsionDto.getSupplyEnveloppe();
        List<BacItem> bacItems = new ArrayList<>();

        // Call Feign client to get Bacs
        List<Bac> bacs = stockRestClient.getBacsByProductAndZone(nameProduct, zoneDepot);
        bacs.forEach(b->{
            BacItem bacItem = BacItem.builder()
                    .idBac(b.getIdBac())
                    .idDepot(b.getIdDepot())
                    .idProduct(b.getIdProduct())
                    .build();
            bacItems.add(bacItem);
        });
        return bacItems;
    }


    public boolean sameMonthAndYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
    public List<EsDto> getEsDtos(){
        List<EsDto> esDtoList = stockRestClient.getEs();
        return esDtoList;
    }
    public double calculerVreel(Long idPrevisionDto)throws EntityNotFoundException{
        if(idPrevisionDto == null){
            log.error("value is null");
            return 0;
        }
        Prevision prevision = previsionRepository.findById(idPrevisionDto).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);
        List<EsDto> esDtoList = getEsDtos();
        double sumOfSorties = esDtoList.stream()
                .filter(es -> previsionDto.getBusiness().equals(es.getBusiness())
                        && sameMonthAndYear(es.getDate(), previsionDto.getDate())
                        && previsionDto.getBacItems().stream().anyMatch(bacItem -> bacItem.getIdBac().equals(es.getIdBac())))
                .mapToDouble(EsDto::getQuantite)
                .sum();
        return sumOfSorties;
    }




}
