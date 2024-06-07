package net.lahlalia.produit.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.exceptions.ProductNotFoundException;
import net.lahlalia.produit.mappers.MapperProduct;
import net.lahlalia.produit.mappers.MapperRegime;
import net.lahlalia.produit.repositories.ProduitRepository;
import net.lahlalia.produit.repositories.RegimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final MapperProduct productMapper;
    private final RegimeRepository regimeRepository;
    private final MapperRegime regimeMapper;
    private final RegimeService regimeService;



    public ProductDto getProductById(Long idProduit) throws ProductNotFoundException{
        if(idProduit == null){
            log.error("idProduit is null");
            return null;
        }
        ProductDto productDto =  produitRepository.findById(idProduit).map(productMapper::toModel)
                .orElseThrow(
                        ()-> new ProductNotFoundException("product not found with specific id : " + idProduit)
                );
        Regime regime = produitRepository.findById(idProduit).get().getRegime();
        RegimeDto regimeDto = regimeMapper.toModel(regime);
        productDto.setRegime(regimeDto);
        return productDto;



    }

    /**
     * .stream() : La méthode stream() est appelée sur la collection de produits récupérée par findAll(). Cela convertit la collection en un flux (stream) d'objets Product. Un flux est une séquence d'éléments sur laquelle vous pouvez appliquer des opérations de transformation ou de filtrage.
     * @return
     */
    public List<ProductDto> getAllProducts(){
        return produitRepository.findAll().stream().map(productMapper::toModel).toList();
    }
//    public Produit createProduct(Produit p)throws EntityNotFoundException{
//        Regime regime = regimeRepository.findById(p.getRegime().getIdRegime())
//                .orElseThrow(
//                        ()-> new EntityNotFoundException("Regime Not found"));
//        p.setRegime(regime);
//        return produitRepository.save(p);
//
//
//    }

    public ProductDto updateProduct(Long id, ProductDto productDto) throws EntityNotFoundException{
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        existingProduit.setName(productDto.getName());
        existingProduit.setType(productDto.getType());
        existingProduit.setStatus(productDto.getStatus());

        if(productDto.getRegime() != null) {
            // Convert RegimeDto to Regime entity
            Regime regimeEntity = regimeMapper.toEntity(productDto.getRegime());
            // Check if Regime entity already exists in the database
            existingProduit.setRegime(regimeEntity);
        } else {
            existingProduit.setRegime(null); // Clear existing Regime if null is provided
        }


        Produit updatedProduit = produitRepository.save(existingProduit);

        return productMapper.toModel(updatedProduit);


    }


    public ProductDto saveProduct(ProductDto dto)throws EntityNotFoundException {
        if(dto == null){
            log.error("product is null");
            return null;
        }

        Produit produit = productMapper.toEntity(dto);
        if(dto.getRegime() != null){
            Regime regime = regimeRepository.findById(dto.getRegime().getIdRegime())
                    .orElseThrow(()-> new EntityNotFoundException("Regime Not found"));
            produit.setRegime(regime);
        }

        Produit savedProduct = produitRepository.save(produit);
        return productMapper.toModel(savedProduct);


    }
    // Regime regime = regimeRepository.findById(dto.getRegimeId()).get();
    // Version 2 :


    // Version 1:
//        return productMapper.toModel(produitRepository.save(
//                productMapper.toEntity(dto)
//        ));


    public void deleteProductById(Long idProduct)throws ProductNotFoundException{
//            ProductDto productDto = getProductById(id);
        Optional<Produit> produit = produitRepository.findById(idProduct);
        if(produit.isEmpty()) throw new ProductNotFoundException("product not found with specific id : " + idProduct);
        produitRepository.deleteById(idProduct);


    }

}
