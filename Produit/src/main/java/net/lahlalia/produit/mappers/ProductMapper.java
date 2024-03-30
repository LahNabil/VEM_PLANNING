package net.lahlalia.produit.mappers;

import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.dtos.ProductDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Produit toEntity(ProductDto dto);
    ProductDto toModel(Produit produit);
}

