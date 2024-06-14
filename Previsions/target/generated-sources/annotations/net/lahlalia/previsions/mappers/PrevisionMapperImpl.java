package net.lahlalia.previsions.mappers;

import javax.annotation.processing.Generated;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.Prevision;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T03:33:46+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PrevisionMapperImpl implements PrevisionMapper {

    @Override
    public Prevision toEntity(PrevisionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Prevision prevision = new Prevision();

        return prevision;
    }

    @Override
    public PrevisionDto toModel(Prevision prevision) {
        if ( prevision == null ) {
            return null;
        }

        PrevisionDto previsionDto = new PrevisionDto();

        return previsionDto;
    }
}
