package net.lahlalia.produit.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.exceptions.ProductNotFoundException;
import net.lahlalia.produit.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>>getAllProducts(){
        List<ProductDto> products = produitService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping(value = "/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long idProduit)throws EntityNotFoundException {
        ProductDto dto = produitService.getProductById(idProduit);
        return ResponseEntity.ok(dto);
    }
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto dto){
        ProductDto savedProduct = produitService.saveProduct(dto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto>updateProduct(@PathVariable Long id,@RequestBody ProductDto dto) throws EntityNotFoundException{
        ProductDto updatedProduct = produitService.updateProduct(id,dto);
        return ResponseEntity.ok(updatedProduct);
    }


//@PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//public ResponseEntity<Produit> saveProduct(@RequestBody Produit produit){
//    Produit savedProduct = produitService.createProduct(produit);
//    return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//
//}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
    try {
        produitService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ProductNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}





}
