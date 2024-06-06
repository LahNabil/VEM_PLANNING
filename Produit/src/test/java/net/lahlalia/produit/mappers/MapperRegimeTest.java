package net.lahlalia.produit.mappers;

import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.assertThat;

class MapperRegimeTest {

    private MapperRegime mapperRegime;
    private MapperProduct mapperProduct;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        mapperRegime = new MapperRegime(modelMapper);
        mapperProduct = new MapperProduct(modelMapper);
        // Extra assertion to ensure the mappers are initialized
        assertThat(mapperRegime).isNotNull();
        assertThat(mapperRegime.toEntity(new RegimeDto())).isNotNull();
        assertThat(mapperRegime.toModel(new Regime())).isNotNull();
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
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedRegime);
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
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedRegime);
    }

    @Test
    public void shouldHandleNullValuesInRegimeDtoToRegime() {
        RegimeDto givenRegimeDto = RegimeDto.builder()
                .idRegime(null)
                .regime(null)
                .build();

        Regime result = mapperRegime.toEntity(givenRegimeDto);
        assertThat(result).isNotNull();
        assertThat(result.getIdRegime()).isNull();
        assertThat(result.getRegime()).isNull();
    }

    @Test
    public void shouldHandleNullValuesInRegimeToRegimeDto() {
        Regime givenRegime = Regime.builder()
                .idRegime(null)
                .regime(null)
                .build();

        RegimeDto result = mapperRegime.toModel(givenRegime);
        assertThat(result).isNotNull();
        assertThat(result.getIdRegime()).isNull();
        assertThat(result.getRegime()).isNull();
    }
}
