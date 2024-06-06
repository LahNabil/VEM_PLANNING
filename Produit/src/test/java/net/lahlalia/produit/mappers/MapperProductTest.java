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

class MapperProductTest {

    private MapperProduct mapperProduct;
    private MapperRegime mapperRegime;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        mapperRegime = new MapperRegime(modelMapper);
        mapperProduct = new MapperProduct(modelMapper);
        assertThat(mapperProduct).isNotNull();
        assertThat(mapperProduct.toEntity(new ProductDto())).isNotNull();
        assertThat(mapperProduct.toModel(new Produit())).isNotNull();
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
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedProduct);
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
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    public void shouldHandleNullValuesInProductDtoToProduct() {
        ProductDto givenProductDto = ProductDto.builder()
                .idProduit(null)
                .name(null)
                .status(null)
                .type(null)
                .regime(null)
                .build();

        Produit result = mapperProduct.toEntity(givenProductDto);
        assertThat(result).isNotNull();
        assertThat(result.getIdProduit()).isNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getStatus()).isNull();
        assertThat(result.getType()).isNull();
        assertThat(result.getRegime()).isNull();
    }

    @Test
    public void shouldHandleNullValuesInProductToProductDto() {
        Produit givenProduct = Produit.builder()
                .idProduit(null)
                .name(null)
                .status(null)
                .type(null)
                .regime(null)
                .build();

        ProductDto result = mapperProduct.toModel(givenProduct);
        assertThat(result).isNotNull();
        assertThat(result.getIdProduit()).isNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getStatus()).isNull();
        assertThat(result.getType()).isNull();
        assertThat(result.getRegime()).isNull();
    }
}
