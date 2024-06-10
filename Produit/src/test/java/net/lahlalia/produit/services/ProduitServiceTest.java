package net.lahlalia.produit.services;

import jakarta.persistence.EntityNotFoundException;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import net.lahlalia.produit.enums.TypeProduit;
import net.lahlalia.produit.exceptions.ProductNotFoundException;
import net.lahlalia.produit.mappers.MapperProduct;
import net.lahlalia.produit.mappers.MapperRegime;
import net.lahlalia.produit.repositories.ProduitRepository;
import net.lahlalia.produit.repositories.RegimeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;
    @Mock
    private RegimeRepository regimeRepository;
    @Mock
    private MapperProduct productMapper;
    @Mock
    private MapperRegime regimeMapper;
    @InjectMocks
    private ProduitService underTest;
    private RegimeDto regimeDto;
    private Regime regime;


    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        regimeMapper = new MapperRegime(modelMapper);
        regime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        regimeDto = regimeMapper.toModel(regime);
    }

    @Test
    void shouldReturnNullWhenGetProductByIdWithNullId() {
        ProductDto result = underTest.getProductById(null);
        assertNull(result);
    }
    @Test
    void saveProduct_withNullRegime_shouldReturnExpectedDto() {
        ProductDto productDto = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).build();
        Produit produit = Produit.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).build();
        Produit savedProduct = Produit.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).build();
        ProductDto expected = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).build();

        Mockito.when(productMapper.toEntity(productDto)).thenReturn(produit);
        Mockito.when(produitRepository.save(produit)).thenReturn(savedProduct);
        Mockito.when(productMapper.toModel(savedProduct)).thenReturn(expected);

        ProductDto result = underTest.saveProduct(productDto);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldFindProductById() {
        Long productId = 99L;
        Produit produit = Produit.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime).build();
        ProductDto expected = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto)
                .build();
        Mockito.when(produitRepository.findById(productId)).thenReturn(Optional.of(produit));
        Mockito.when(productMapper.toModel(produit)).thenReturn(expected);
        ProductDto result = underTest.getProductById(productId);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldNotFindProductById() {
        Long productId = 101L;
        Mockito.when(produitRepository.findById(productId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.getProductById(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("product not found with specific id : " + productId);
    }

    @Test
    void shouldGetAllProducts() {

        Produit produit1 = Produit.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime).build();
        Produit produit2 =  Produit.builder().idProduit(100L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL).regime(regime).build();

        ProductDto productDto1 = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto)
                        .build();
        ProductDto productDto2 =ProductDto.builder().idProduit(100L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto)
                        .build();

        List<Produit> products = Arrays.asList(produit1, produit2);
        List<ProductDto> expected = Arrays.asList(productDto1, productDto2);

        Mockito.when(produitRepository.findAll()).thenReturn(products);
        Mockito.when(productMapper.toModel(produit1)).thenReturn(productDto1);
        Mockito.when(productMapper.toModel(produit2)).thenReturn(productDto2);
        List<ProductDto> result = underTest.getAllProducts();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void updateProduct_withValidIdAndDto_shouldReturnUpdatedDto() {
        // Mettez en place les données nécessaires pour votre test
        Long productId = 99L;
        ProductDto productDto = ProductDto.builder().idProduit(productId).name("Gasoil SH").status("Inactif").type(TypeProduit.GASOIL).regime(regimeDto).build();
        Produit produit = Produit.builder().idProduit(productId).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime).build();
        Produit updatedProduit = Produit.builder().idProduit(productId).name("Gasoil SH").status("Inactif").type(TypeProduit.GASOIL).regime(regime).build();
        ProductDto expected = ProductDto.builder().idProduit(productId).name("Gasoil SH").status("Inactif").type(TypeProduit.GASOIL).regime(regimeDto).build();

        // Configurez les comportements attendus pour les mocks
        Mockito.when(produitRepository.findById(productId)).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(produit)).thenReturn(updatedProduit);
        Mockito.when(productMapper.toModel(updatedProduit)).thenReturn(expected);

        // Appelez la méthode à tester
        ProductDto result = underTest.updateProduct(productId, productDto);


        // Vérifiez le résultat
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


    @Test
    void saveProduct_withValidDtoAndRegime_shouldReturnExpectedDto() {
        ProductDto productDto = ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto)
                .build();
        Produit produit= Produit.builder()
                .idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime)
                .build();
        Produit savedProduct= Produit.builder()
                .idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime)
                .build();
        ProductDto expected= ProductDto.builder()
                .idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto)
                .build();
        Mockito.when(productMapper.toEntity(productDto)).thenReturn(produit);
        Mockito.when(regimeRepository.findById(99L)).thenReturn(Optional.of(regime));
        Mockito.when(produitRepository.save(produit)).thenReturn(savedProduct);
        Mockito.when(productMapper.toModel(savedProduct)).thenReturn(expected);
        ProductDto result = underTest.saveProduct(productDto);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


    @Test
    void shouldDeleteProduct() {
        Long productId =7L;
        Produit produit= Produit.builder()
                .idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regime)
                .build();
        Mockito.when(produitRepository.findById(productId)).thenReturn(Optional.of(produit));
        underTest.deleteProductById(productId);
        Mockito.verify(produitRepository).deleteById(productId);
    }
    @Test
    void shouldNotDeleteProductIfNotExist() {
        Long productId =100L;
        Mockito.when(produitRepository.findById(productId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->underTest.deleteProductById(productId))
                .isInstanceOf(ProductNotFoundException.class);
    }
}