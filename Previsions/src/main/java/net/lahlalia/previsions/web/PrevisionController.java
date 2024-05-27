package net.lahlalia.previsions.web;


import lombok.RequiredArgsConstructor;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import net.lahlalia.previsions.services.BacItemService;
import net.lahlalia.previsions.services.PrevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/prevision")
@RequiredArgsConstructor
public class PrevisionController {

    private final BacItemService bacItemService;
    private final PrevisionService previsionService;


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
//    @GetMapping("/bac/{idPrevision}")
//    public ResponseEntity<List<Bac>> getBacsByIdPrevision(@PathVariable Long idPrevision){
//        List<Bac> bacs = previsionService.getBacsByProdZonePrevision(idPrevision);
//        return ResponseEntity.ok(bacs);
//
//    }
    @GetMapping("/{idPrevision}")
    public ResponseEntity<PrevisionDto> getPrevisionById(@PathVariable Long idPrevision){
//        PrevisionDto previsionDto = previsionService.getPrevisionById(idPrevision);
//        return ResponseEntity.ok(previsionDto);
        PrevisionDto previsionDto = previsionService.getPrevisionById(idPrevision);
        return ResponseEntity.ok(previsionDto);
    }
    @PostMapping("/")
    public ResponseEntity<PrevisionDto> savePrevision(@RequestBody PrevisionDto previsionDto){
        PrevisionDto savedPrevision = previsionService.savePrevision(previsionDto);
        return new ResponseEntity<>(savedPrevision, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<PrevisionDto>>getPrevisions(){
        List<PrevisionDto> previsionDtos = previsionService.getAllPrevision();
        return ResponseEntity.ok(previsionDtos);
    }



//    @GetMapping("/bac/{nameProduct}")
//    public ResponseEntity<List<BacItem>> getBacsByProductName(@PathVariable String nameProduct){
//        List<BacItem> bacItems = bacItemService.getBacItemsBynameProduct(nameProduct);
//        return ResponseEntity.ok(bacItems);
//    }

}

