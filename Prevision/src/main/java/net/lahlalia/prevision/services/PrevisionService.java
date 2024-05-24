package net.lahlalia.prevision.services;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.prevision.dtos.Bac;
import net.lahlalia.prevision.dtos.PrevisionDto;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.entities.Prevision;
import net.lahlalia.prevision.mappers.PrevisionMapper;
import net.lahlalia.prevision.repositories.PrevisionRepository;
import net.lahlalia.prevision.restclients.StockRestClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrevisionService {

    private final PrevisionRepository previsionRepository;
    private final PrevisionMapper previsionMapper;
    private final StockRestClient stockRestClient;

//    public PrevisionDto savePrevision(PrevisionDto previsionDto)throws EntityNotFoundException{
//        if(previsionDto == null){
//            log.error(" value is null");
//            return null;
//        }
//
//        Prevision prevision = previsionMapper.toEntity(previsionDto);
//        if(previsionDto.getNameProduct() != null ){
//            List<BacItem> bacs = stockRestClient.getBacsByProductName(previsionDto.getNameProduct());
//            prevision.setBacItems(bacs);
//        }
//
//    }

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
