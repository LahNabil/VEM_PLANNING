package net.lahlalia.produit.mappers;

import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import net.lahlalia.produit.enums.TypeProduit;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class MapperProductTest {

    private MapperProduct mapperProduct;
    private MapperRegime mapperRegime;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        mapperRegime = new MapperRegime(modelMapper);
        mapperProduct = new MapperProduct(modelMapper);
    }



    @Test
    public void shouldMapProductToProductDto(){
        Regime regime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto regimeDto = mapperRegime.toModel(regime);

        Produit givenProduct = Produit.builder()
                .idProduit(99L)
                .name("Gasoil SH")
                .status("Actif")
                .type(TypeProduit.GASOIL)
                .regime(regime)
                .build();
        ProductDto expectedProduct = ProductDto.builder()
                .idProduit(99L)
                .name("Gasoil SH")
                .status("Actif")
                .type(TypeProduit.GASOIL)
                .regime(regimeDto)
                .build();
        ProductDto result = mapperProduct.toModel(givenProduct);
        assertThat(expectedProduct).isNotNull();
        assertThat(expectedProduct).usingRecursiveComparison().isEqualTo(result);

    }
    @Test
    public void shouldMapProductDtoToProduct(){
        Regime regime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto regimeDto = mapperRegime.toModel(regime);


        ProductDto givenProductDto = ProductDto.builder()
                .idProduit(99L)
                .name("Gasoil SH")
                .status("Actif")
                .type(TypeProduit.GASOIL)
                .regime(regimeDto)
                .build();

        Produit expectedProduct = Produit.builder()
                .idProduit(99L)
                .name("Gasoil SH")
                .status("Actif")
                .type(TypeProduit.GASOIL)
                .regime(regime)
                .build();

        Produit result = mapperProduct.toEntity(givenProductDto);

        assertThat(expectedProduct).isNotNull();
        assertThat(expectedProduct).usingRecursiveComparison().isEqualTo(result);

    }

}