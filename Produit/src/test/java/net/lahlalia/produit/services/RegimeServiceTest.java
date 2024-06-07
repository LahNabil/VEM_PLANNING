package net.lahlalia.produit.services;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import net.lahlalia.produit.enums.TypeProduit;
import net.lahlalia.produit.mappers.MapperRegime;
import net.lahlalia.produit.repositories.RegimeRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class RegimeServiceTest {

    @Mock
    private RegimeRepository regimeRepository;
    @Mock
    private MapperRegime regimeMapper;
    @InjectMocks
    private RegimeService underTest;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        MapperRegime mapperRegime = new MapperRegime(modelMapper);
        underTest = new RegimeService(regimeRepository, mapperRegime);
    }

    @Test
    void getRegimeById() {
        Long idRegime = 99L;
        Regime regime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto expected = RegimeDto.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        Mockito.when(regimeRepository.findById(idRegime)).thenReturn(Optional.of(regime));
        RegimeDto result = underTest.getRegimeById(idRegime);
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void getAllRegimes() {
        Regime regime1 = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        Regime regime2 = Regime.builder()
                .idRegime(100L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto regimeDto1 = RegimeDto.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        RegimeDto regimeDto2 = RegimeDto.builder()
                .idRegime(100L)
                .regime(RegimeType.DEDOUANE)
                .build();

        List<Regime> regimes = Arrays.asList(regime1, regime2);
        List<RegimeDto> expected = Arrays.asList(regimeDto1, regimeDto2);

//        Mockito.when(regimeMapper.toModel(regime1)).thenReturn(regimeDto1);
//        Mockito.when(regimeMapper.toModel(regime2)).thenReturn(regimeDto2);
        List<RegimeDto> result = underTest.getAllRegimes();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    void saveRegime() {
//        Produit produit1 = Produit.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).build();
//        Produit produit2 =  Produit.builder().idProduit(100L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL).build();
//
//        ProductDto productDto1 = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL)
//                .build();
//        ProductDto productDto2 =ProductDto.builder().idProduit(100L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL)
//                .build();
//
//        List<Produit> productList = Arrays.asList(produit1, produit2);
//        List<ProductDto> productDtoList = Arrays.asList(productDto1, productDto2);
//
//        Regime regime = Regime.builder()
//                .idRegime(99L)
//                .regime(RegimeType.DEDOUANE)
//                .produits(productList)
//                .build();
//        RegimeDto regimeDto = RegimeDto.builder()
//                .idRegime(99L)
//                .regime(RegimeType.DEDOUANE)
//                .build();
//        Regime savedRegime = Regime.builder()
//                .idRegime(99L)
//                .regime(RegimeType.DEDOUANE)
//                .produits(productList)
//                .build();
//        RegimeDto expected = RegimeDto.builder()
//                .idRegime(99L)
//                .regime(RegimeType.DEDOUANE)
//                .build();
//        Mockito.when(regimeMapper.toEntity(regimeDto)).thenReturn(regime);
//        Mockito.when(regimeRepository.findById(99L)).thenReturn(Optional.of(regime));
//        Mockito.when(regimeRepository.save(regime)).thenReturn(savedRegime);
//        Mockito.when(regimeMapper.toModel(savedRegime)).thenReturn(expected);
//        RegimeDto result = underTest.saveRegime(regimeDto);
//        AssertionsForClassTypes.assertThat(result).isNotNull();
//        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    void deleteRegime() {
    }
}
