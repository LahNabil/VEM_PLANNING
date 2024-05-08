package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.ESDto;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.mappers.ESMapper;
import net.lahlalia.stock.repositories.EntreSortieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EsService {
    private final EntreSortieRepository entreSortieRepository;
    private final ESMapper esMapper;

    public List<ESDto> getAllES(){
        List<EntreSortie> ESList = entreSortieRepository.findAll();
        List<ESDto> esDtoList = new ArrayList<>();
        for(EntreSortie es : ESList){
            ESDto esDto = esMapper.toModel(es);
            try{
                esDto.setIdBac(es.getBac().getIdBac());
            }catch (EntityNotFoundException e) {

                log.error("Bac not found for Bac ID: " + es.getId());
            }
            esDtoList.add(esDto);
        }
        return esDtoList;
//       return entreSortieRepository.findAll().stream().map(esMapper::toModel).toList();
    }
//
}
