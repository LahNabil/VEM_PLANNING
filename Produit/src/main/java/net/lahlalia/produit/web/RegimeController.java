package net.lahlalia.produit.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.services.RegimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regimes")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RegimeController {

    private final RegimeService regimeService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegimeDto>> getAllRegime(){
        List<RegimeDto> regimes = regimeService.getAllRegimes();
        return ResponseEntity.ok(regimes);
    }
    @GetMapping(value = "/{idRegime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegimeDto> getRegimeById(@PathVariable Long idRegime)throws EntityNotFoundException {
        RegimeDto dto = regimeService.getRegimeById(idRegime);
        return ResponseEntity.ok(dto);
    }
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegimeDto> saveRegime(@RequestBody RegimeDto dto){
        RegimeDto savedDto = regimeService.saveRegime(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
}
