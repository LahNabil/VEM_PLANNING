package net.lahlalia.prevision.restclients;

import net.lahlalia.prevision.dtos.Bac;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "STOCK-SERVICE")
public interface StockRestClient {

    @GetMapping("/api/bac/")
    public List<Bac> getAllProducts();

    @GetMapping("/api/bac/{idBac}")
    public Bac getProductById(@PathVariable Long idBac);


}
