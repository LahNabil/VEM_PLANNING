package net.lahlalia.produit.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.produit.dtos.RegimeDto;
import net.lahlalia.produit.mappers.RegimeMapper;
import net.lahlalia.produit.repositories.RegimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegimeService {
    private final RegimeRepository regimeRepository;
    private final RegimeMapper regimeMapper;

    public RegimeDto getRegimeById(Long idRegime) throws EntityNotFoundException{
        if(idRegime == null){
            log.error("idRegime is null");
            return null;
        }
        return regimeRepository.findById(idRegime)
                .map(regimeMapper::toModel)
                .orElseThrow(
                        ()-> new EntityNotFoundException("Rgime not found with id: "+idRegime)
                );

    }
    public List<RegimeDto> getAllRegimes(){
        return regimeRepository.findAll()
                .stream().map(regimeMapper::toModel).toList();
    }

    public RegimeDto saveRegime(RegimeDto dto){
        return regimeMapper.toModel(
                regimeRepository.save(
                        regimeMapper.toEntity(dto)
                )
        );

    }

    public Boolean deleteRegime(Long id){
        RegimeDto dto = getRegimeById(id);
        if(dto != null){
            regimeRepository.deleteById(id);
            return true;
        }
        return false;
    }




}
