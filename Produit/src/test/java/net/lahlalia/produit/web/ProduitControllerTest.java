package net.lahlalia.produit.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Regime;
import net.lahlalia.produit.enums.RegimeType;
import net.lahlalia.produit.enums.TypeProduit;
import net.lahlalia.produit.exceptions.ProductNotFoundException;
import net.lahlalia.produit.mappers.MapperRegime;
import net.lahlalia.produit.services.ProduitService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProduitController.class)
class ProduitControllerTest {

    @MockBean
    private ProduitService produitService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private MapperRegime regimeMapper;

    private RegimeDto regimeDto;
    private Regime regime;

    List<ProductDto> productDtoList;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        regimeMapper = new MapperRegime(modelMapper);
        regime = Regime.builder()
                .idRegime(99L)
                .regime(RegimeType.DEDOUANE)
                .build();
        regimeDto = regimeMapper.toModel(regime);
        this.productDtoList = List.of(
                ProductDto.builder().idProduit(99L).name("Gasoil SH").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto).build(),
                ProductDto.builder().idProduit(100L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto).build(),
                ProductDto.builder().idProduit(101L).name("Gasoil").status("Actif").type(TypeProduit.GASOIL).regime(regimeDto).build()
                );
    }

    @Test
    void shouldGetAllProducts()throws Exception {
        Mockito.when(produitService.getAllProducts()).thenReturn(productDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productDtoList)));
    }

    @Test
    void getProductById()throws Exception{
        Long idProduct = 99L;
        Mockito.when(produitService.getProductById(idProduct)).thenReturn(productDtoList.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{idProduct}",idProduct))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productDtoList.get(0))));
    }

    @Test
    void shouldNotGetProductByInvalidId()throws Exception{
        Long idProduct = 1L;
        Mockito.when(produitService.getProductById(idProduct)).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{idProduct}",idProduct))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));


    }

    @Test
    void saveProduct()throws Exception {
        ProductDto productDto = productDtoList.get(0);
        ProductDto newProduct = ProductDto.builder()
                .idProduit(102L)
                .name("New Product")
                .status("Actif")
                .type(TypeProduit.GASOIL)
                .regime(regimeDto)
                .build();

        Mockito.when(produitService.saveProduct(Mockito.any(ProductDto.class))).thenReturn(newProduct);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(newProduct)));
    }

    @Test
    void updateProduct() throws Exception{
        Long idProduct=99L;
        ProductDto productDto= productDtoList.get(0);
        Mockito.when(produitService.updateProduct(Mockito.eq(idProduct),Mockito.any())).thenReturn(productDtoList.get(0));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", idProduct)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    void deleteProductById() throws Exception{
        Long idProduct=99L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{id}",idProduct))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}