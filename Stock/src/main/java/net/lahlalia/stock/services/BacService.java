package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.Product;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.mappers.BacMapper;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.restClients.ProductRestClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BacService {
    private final BacRepository bacRepository;
    private final BacMapper bacMapper;
    private final ProductRestClient productRestClient;

    public BacDto getBacById(String idBac ) throws EntityNotFoundException {
        if(idBac == null){
            log.error("id Bac is null");
            return null;
        }
        Bac bac = bacRepository.findById(idBac).get();
        BacDto bacDto =  bacMapper.toModel(bac);
        Product p = productRestClient.getProductById(bac.getIdProduct());
        bacDto.setProduct(p);
        return bacDto;
    }
    public List<BacDto> getAllBacs(){
        return bacRepository.findAll().stream().map(bacMapper::toModel).toList();
    }

//    public BacDto saveBac(BacDto dto)throws EntityNotFoundException {
//        if(dto == null){
//            log.error("Bac is null");
//            return null;
//        }
//
//        Product product = productRestClient.getProductById(dto.getProductId());
//        if(product == null){
//            log.error("product is null");
//        }
//        Bac bac = bacMapper.toEntity(dto);
//
//
//        Bac savedBac = bacRepository.save(bac);
//        return bacMapper.toModel(savedBac);
//
//
//    }
}
