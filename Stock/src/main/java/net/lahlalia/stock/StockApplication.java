package net.lahlalia.stock;

import net.lahlalia.stock.dtos.Product;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.restClients.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
    /*@Bean
    CommandLineRunner commandLineRunner(
			BacRepository bacRepository,
			ProductRestClient productRestClient
	){
        return args -> {
            List<Product> allProducts = productRestClient.getAllProducts();
            allProducts.forEach(
                    p ->{
                        System.out.println(p.getName());
                    }
            );
        };
    }*/

}
