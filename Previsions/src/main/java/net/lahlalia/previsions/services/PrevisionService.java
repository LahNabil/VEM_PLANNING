package net.lahlalia.previsions.services;




import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import net.lahlalia.previsions.mappers.BacMapper;
import net.lahlalia.previsions.mappers.MapperPrevision;
import net.lahlalia.previsions.mappers.PrevisionMapper;
import net.lahlalia.previsions.repositories.PrevisionRepository;
import net.lahlalia.previsions.restclients.StockRestClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrevisionService {

    private final PrevisionRepository previsionRepository;
    private final StockRestClient stockRestClient;
    private final MapperPrevision mapperPrevision;


    public PrevisionDto savePrevision(PrevisionDto dto){
        return mapperPrevision.convertToDto(
                previsionRepository.save(
                        mapperPrevision.convertToModel(dto)
                )
        );

    }
//    public PrevisionDto savePrevision(PrevisionDto previsionDto)throws EntityNotFoundException{
////        if(previsionDto == null){
////            log.error(" value is null");
////            return null;
////        }
////
////        Prevision prevision = previsionMapper.toEntity(previsionDto);
//////        List<BacItem> bacs = stockRestClient.getBacsByProductAndZone(previsionDto.getNameProduct(),previsionDto.getSupplyEnveloppe()).stream().map(bacMapper::toEntity).toList();
//////        prevision.setBacItems(bacs);
////        Prevision savedPrevision = previsionRepository.save(prevision);
////        return previsionMapper.toModel(savedPrevision);
//
//
//    }
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
    public List<Bac> getBacsByProdZonePrevision(Long idPrevision) {
        Prevision prevision = previsionRepository.findById(idPrevision)
                .orElseThrow(() -> new EntityNotFoundException("Prevision Not found"));
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        // Check for null values
        if (previsionDto.getNameProduct() == null || previsionDto.getSupplyEnveloppe() == null) {
            throw new IllegalArgumentException("NameProduct or SupplyEnveloppe is null");
        }

        String nameProduct = previsionDto.getNameProduct();
        String zoneDepot = previsionDto.getSupplyEnveloppe();

        // Call Feign client to get Bacs
        List<Bac> bacs = stockRestClient.getBacsByProductAndZone(nameProduct, zoneDepot);
        return bacs;
    }


//    public BacDto getBacById(String idBac ) throws EntityNotFoundException {
//        if(idBac == null){
//            log.error("id Bac is null");
//            return null;
//        }
//        Bac bac = bacRepository.findById(idBac).get();
//        BacDto bacDto =  bacMapper.toModel(bac);
//        bacDto.setIdProduct(bac.getIdProduct());
//        bacDto.setIdDepot(bac.getDepot().getIdDepot());
//        return bacDto;
//    }
//    public PrevisionDto getPrevisionById(Long idPrevision) throws EntityNotFoundException{
//        if(idPrevision == null){
//            log.error("id Prevision is null");
//            return null;
//        }
//        Prevision prevision = previsionRepository.findById(idPrevision).get();
//        PrevisionDto previsionDto = previsionMapper.toModel(prevision);
//
//    }



}
