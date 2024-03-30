package net.lahlalia.produit.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.mappers.ProductMapper;
import net.lahlalia.produit.repositories.ProduitRepository;
import net.lahlalia.produit.repositories.RegimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final ProductMapper productMapper;
    private final RegimeRepository regimeRepository;

    public ProduitService(ProduitRepository produitRepository,RegimeRepository regimeRepository,ProductMapper productMapper) {
        this.produitRepository = produitRepository;
        this.productMapper = productMapper;
        this.regimeRepository = regimeRepository;

    }

    public ProductDto getProductById(Long idProduit) throws EntityNotFoundException {
        if(idProduit == null){
            log.error("idProduit is null");
            return null;
        }
        return produitRepository.findById(idProduit).map(productMapper::toModel)
                .orElseThrow(
                        ()-> new EntityNotFoundException("product not found with specific id : " + idProduit)
                );

    }

    /**
     * .stream() : La méthode stream() est appelée sur la collection de produits récupérée par findAll(). Cela convertit la collection en un flux (stream) d'objets Product. Un flux est une séquence d'éléments sur laquelle vous pouvez appliquer des opérations de transformation ou de filtrage.
     * @return
     */
    public List<ProductDto> getAllProducts(){
        return produitRepository.findAll().stream().map(productMapper::toModel).toList();
    }

    public ProductDto saveProduct(ProductDto dto){

        return productMapper.toModel(produitRepository.save(
                productMapper.toEntity(dto)
        ));
    }

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
