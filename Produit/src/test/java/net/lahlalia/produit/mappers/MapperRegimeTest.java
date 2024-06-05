package net.lahlalia.produit.mappers;

import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import net.lahlalia.produit.enums.TypeProduit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapperRegimeTest {

    private MapperProduct mapperProduct;
    private MapperRegime mapperRegime;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        mapperRegime = new MapperRegime(modelMapper);
        mapperProduct = new MapperProduct(modelMapper);
    }



    @Test
    public void MapRegimeToRegimeDto(){

        Regime givenRegime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();

        RegimeDto expectedRegime = RegimeDto.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto result = mapperRegime.toModel(givenRegime);
        assertThat(expectedRegime).isNotNull();
        assertThat(expectedRegime).usingRecursiveComparison().isEqualTo(result);

    }
    @Test
    public void MapRegimeDtoToRegime(){

        RegimeDto givenRegimeDto = RegimeDto.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();

        Regime expectedRegime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();

        Regime result = mapperRegime.toEntity(givenRegimeDto);

        assertThat(expectedRegime).isNotNull();
        assertThat(expectedRegime).usingRecursiveComparison().isEqualTo(result);

    }

}