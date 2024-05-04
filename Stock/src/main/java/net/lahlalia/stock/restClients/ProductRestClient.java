package net.lahlalia.stock.restClients;

import net.lahlalia.stock.dtos.Product;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {
    public List<Product> getAllProducts();
}
