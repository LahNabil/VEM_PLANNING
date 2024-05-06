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

import java.util.ArrayList;
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
        bacDto.setIdProduct(bac.getIdProduct());
        return bacDto;
    }
    public List<BacDto> getAllBacs(){
//        return bacRepository.findAll().stream().map(bacMapper::toModel).toList();
        List<Bac> bacList = bacRepository.findAll();
        List<BacDto> bacsDto = new ArrayList<>();
        for (Bac bac : bacList) {
            BacDto bacDto = bacMapper.toModel(bac);
            try {
                bacDto.setIdProduct(bac.getIdProduct());
            } catch (EntityNotFoundException e) {
                // Gérer l'exception si le produit n'est pas trouvé
                // Vous pouvez choisir de ne pas ajouter le produit au BacDto dans ce cas
                log.error("Product not found for Bac ID: " + bac.getIdProduct());
            }
            bacsDto.add(bacDto);
        }
        return bacsDto;
    }

    public BacDto saveBac(BacDto dto)throws EntityNotFoundException {
        if(dto == null){
            log.error("Bac is null");
            return null;
        }
        Bac bac = bacMapper.toEntity(dto);
        if(dto.getIdProduct() != null){
            bac.setIdProduct(dto.getIdProduct());
        }

        Bac savedBac = bacRepository.save(bac);
        return bacMapper.toModel(savedBac);


    }
}
