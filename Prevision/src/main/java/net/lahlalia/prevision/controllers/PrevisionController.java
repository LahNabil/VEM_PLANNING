package net.lahlalia.prevision.controllers;


import lombok.RequiredArgsConstructor;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.services.BacItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/prevision")
@RequiredArgsConstructor
public class PrevisionController {

    private final BacItemService bacItemService;


    @GetMapping("/bac")
    public ResponseEntity<List<BacItem>>getAllBacs(){
        List<BacItem> bacItems = bacItemService.getAllBacItems();
        return ResponseEntity.ok(bacItems);
    }
    @GetMapping("/bac/name/{idBac}")
    public ResponseEntity<String> getProductNameById(@PathVariable Long idBac){
        String nameProduct = bacItemService.getProductNameByIDBac(idBac);
        return ResponseEntity.ok(nameProduct);
    }

//    @GetMapping("/bac/{nameProduct}")
//    public ResponseEntity<List<BacItem>> getBacsByProductName(@PathVariable String nameProduct){
//        List<BacItem> bacItems = bacItemService.getBacItemsBynameProduct(nameProduct);
//        return ResponseEntity.ok(bacItems);
//    }

}
