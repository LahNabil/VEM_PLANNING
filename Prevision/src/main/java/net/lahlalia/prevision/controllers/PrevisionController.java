package net.lahlalia.prevision.controllers;


import lombok.RequiredArgsConstructor;
import net.lahlalia.prevision.entities.BacItem;
import net.lahlalia.prevision.services.BacItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
