package net.lahlalia.prevision.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.prevision.dtos.Bac;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.repositories.BacItemRepository;
import net.lahlalia.prevision.restclients.StockRestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BacItemService {

    private final BacItemRepository bacItemRepository;
    private final StockRestClient stockRestClient;


//    public List<BacItem> getBacItemsBynameProduct(String nameProduct){
//        List<Bac> bacs = stockRestClient.getBacsByProductName(nameProduct);
//        List<BacItem> bacItems = bacItemRepository.findAll();
//        Set<String> bacItemIds = bacItems.stream()
//                .map(BacItem::getIdBac)
//                .collect(Collectors.toSet());
//
//    }
    public String getProductNameByIDBac(Long id){
        BacItem bacItem = bacItemRepository.findById(id).get();
        String nameProduct = stockRestClient.getProductNameById(bacItem.getIdProduct());
        return nameProduct;
    }

    public List<BacItem> getAllBacItems(){
        List<Bac> allBacs = stockRestClient.getAllBacs();
        List<BacItem> bacItems = bacItemRepository.findAll();

        Set<String> bacItemIds = bacItems.stream()
                .map(BacItem::getIdBac)
                .collect(Collectors.toSet());

        for(Bac bac : allBacs){
            if(!bacItemIds.contains(bac.getIdBac())){
                BacItem newBacItem = BacItem.builder()
                        .idBac(bac.getIdBac())
                        .idDepot(bac.getIdDepot())
                        .idProduct(bac.getIdProduct())
//                        .nameProduct(stockRestClient.getProductNameById(bac.getIdProduct()))
                        .build();
                bacItems.add(newBacItem);
                bacItemRepository.save(newBacItem);
            }
        }
        return bacItems;


    }
}
