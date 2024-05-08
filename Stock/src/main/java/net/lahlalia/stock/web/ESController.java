package net.lahlalia.stock.web;

import lombok.RequiredArgsConstructor;
import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.services.EsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
