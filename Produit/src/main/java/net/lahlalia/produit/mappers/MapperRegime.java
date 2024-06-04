package net.lahlalia.produit.mappers;


import lombok.RequiredArgsConstructor;
import net.lahlalia.produit.dtos.ProductDto;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.entities.Produit;
import net.lahlalia.produit.entities.Regime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperRegime {
    private final ModelMapper mapper;

    public Regime toEntity(RegimeDto dto){
        Regime regime = mapper.map(dto, Regime.class);
        return regime;
    }
    public RegimeDto toModel(Regime regime){
        RegimeDto regimeDto = mapper.map(regime, RegimeDto.class);
        return regimeDto;

    }
}
