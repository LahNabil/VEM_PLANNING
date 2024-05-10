package net.lahlalia.stock.web;

import lombok.RequiredArgsConstructor;
import net.lahlalia.stock.dtos.DepotDTO;
import net.lahlalia.stock.services.DepotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/depot")
@RequiredArgsConstructor
public class DepotController {

    private final DepotService depotService;

    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepotDTO>saveDepot(@RequestBody DepotDTO dto){
        DepotDTO savedDepot = depotService.saveDepot(dto);
        return new ResponseEntity<>(savedDepot, HttpStatus.CREATED);

    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DepotDTO>> getAllDepot(){
        List<DepotDTO> depots = depotService.getDepots();
        return ResponseEntity.ok(depots);
    }
}
