package net.lahlalia.stock.web;

import lombok.RequiredArgsConstructor;
import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.dtos.StockEsDto;
import net.lahlalia.stock.services.EsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/es")
@RequiredArgsConstructor
public class ESController {
    private final EsService esService;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ESDto>> getAllES(){
        List<ESDto> esDtoList = esService.getAllES();
        return ResponseEntity.ok(esDtoList);

    }

    @GetMapping(value = "/{idDepot}/{nameProduct}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ESDto>> getSortiesParProduitDepot(@PathVariable String idDepot,@PathVariable String nameProduct){
        List<ESDto> esDtoList = esService.getSortiesParProduitDepot(idDepot,nameProduct);
        return ResponseEntity.ok(esDtoList);
    }
    @GetMapping(value = "/entreeByMonth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getEntreeByMonth() {
        double totalQuantity = esService.GetEntreebymonth();
        return ResponseEntity.ok(totalQuantity);
    }
    @GetMapping(value = "/sortieByMonth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getSortiebymonth(){
        double totalQuantity = esService.getSortiebymonth();
        return ResponseEntity.ok(totalQuantity);
    }
    @GetMapping(value = "/{idDepot}/{nameProduct}/{year}/{month}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ESDto>> getSortiesParProduitDepotDate(@PathVariable String idDepot,@PathVariable String nameProduct,@PathVariable int year,@PathVariable int month) {
        List<ESDto> esDtoList = esService.getSortiesParProduitDepotDate(idDepot, nameProduct,year,month);
        return ResponseEntity.ok(esDtoList);
    }
    @GetMapping("montly/{idDepot}/{nameProduct}/{year}/{month}")
    public ResponseEntity<List<StockEsDto>>generateMonthlyStockReport(@PathVariable String idDepot,
                                                                      @PathVariable String nameProduct,
                                                                      @PathVariable int year,
                                                                      @PathVariable int month){
        List<StockEsDto> stockEsDtoList = esService.generateMonthlyStockReport(idDepot,nameProduct,year,month);
        return ResponseEntity.ok(stockEsDtoList);
    }


}