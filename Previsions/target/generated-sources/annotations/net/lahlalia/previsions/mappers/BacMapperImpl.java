package net.lahlalia.previsions.mappers;

import javax.annotation.processing.Generated;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.entities.BacItem;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T03:33:45+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class BacMapperImpl implements BacMapper {

    @Override
    public BacItem toEntity(Bac dto) {
        if ( dto == null ) {
            return null;
        }

        BacItem bacItem = new BacItem();

        return bacItem;
    }

    @Override
    public Bac toModel(BacItem bacItem) {
        if ( bacItem == null ) {
            return null;
        }

        Bac bac = new Bac();

        return bac;
    }
}
