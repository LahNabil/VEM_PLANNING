package net.lahlalia.produit.mappers;

import lombok.RequiredArgsConstructor;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.entities.Produit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperProduct {

    private final ModelMapper mapper;

    public Produit toEntity(ProductDto dto){
        Produit produit = mapper.map(dto, Produit.class);
        return produit;
    }
    public ProductDto toModel(Produit produit){
        ProductDto productDto = mapper.map(produit, ProductDto.class);
        return productDto;

    }
}
