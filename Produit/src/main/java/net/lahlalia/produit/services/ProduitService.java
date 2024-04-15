package net.lahlalia.produit.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.mappers.ProductMapper;
import net.lahlalia.produit.mappers.RegimeMapper;
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
    private final ProductMapper productMapper;
    private final RegimeRepository regimeRepository;
    private final RegimeMapper regimeMapper;
    private final RegimeService regimeService;



    public ProductDto getProductById(Long idProduit) throws EntityNotFoundException {
        if(idProduit == null){
            log.error("idProduit is null");
            return null;
        }
        ProductDto productDto =  produitRepository.findById(idProduit).map(productMapper::toModel)
                .orElseThrow(
                        ()-> new EntityNotFoundException("product not found with specific id : " + idProduit)
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


    public boolean deleteProductById(Long id)throws EntityNotFoundException{
            ProductDto dto = getProductById(id);
            if( dto != null){
                produitRepository.deleteById(id);
                return true;
            } else {
                return false;
            }

    }

}
