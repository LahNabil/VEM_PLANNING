package net.lahlalia.stock.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.services.BacService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/bac")
@RequiredArgsConstructor
public class BacController {
    private final BacService bacService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BacDto>> getAllBac(){
        List<BacDto> bacs = bacService.getAllBacs();
        return ResponseEntity.ok(bacs);
    }
    @GetMapping(value = "/{idBac}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BacDto> getProductById(@PathVariable String idBac)throws EntityNotFoundException {
        BacDto dto = bacService.getBacById(idBac);
        return ResponseEntity.ok(dto);
    }
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BacDto> saveProduct(@RequestBody BacDto dto){
        BacDto savedBac = bacService.saveBac(dto);
        return new ResponseEntity<>(savedBac, HttpStatus.CREATED);

    }
    @PostMapping("/stock/{idBac}")
    public ResponseEntity<BacDto> ESrProduit(@RequestBody EntreSortie es, @PathVariable String idBac){
        try{
            BacDto savedBacDto = bacService.ESrProduit(es,idBac);
            if (savedBacDto != null) {
                return ResponseEntity.ok(savedBacDto);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }catch (EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
//    @PostMapping("/soustraire/{idBac}")
//    public ResponseEntity<BacDto> SoustraireProduit(@RequestBody EntreSortie es,@PathVariable String idBac){
//        try{
//            BacDto savedBacDto = bacService.soustraireProduit(es,idBac);
//            if(savedBacDto != null){
//                return ResponseEntity.ok(savedBacDto);
//            }else {
//                return ResponseEntity.badRequest().build();
//            }
//        }catch(EntityNotFoundException ex){
//            return ResponseEntity.notFound().build();
//        }
//    }
}
