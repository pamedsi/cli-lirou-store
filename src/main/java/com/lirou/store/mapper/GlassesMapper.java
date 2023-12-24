package com.lirou.store.mapper;

import com.lirou.store.DTOs.GlassesDTO;
import com.lirou.store.entities.Glasses;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlassesMapper {
    public GlassesDTO singleToDTO(Glasses glassesEntity) {
        return new GlassesDTO(
                glassesEntity.getIdentifier(),
                glassesEntity.getTitle(),
                glassesEntity.getPic(),
                glassesEntity.getInStock(),
                glassesEntity.getModel(),
                glassesEntity.getFrame(),
                glassesEntity.getColor(),
                glassesEntity.getBrand(),
                glassesEntity.getPrice()
        );
    }

    public List<GlassesDTO> severalToDTO (List<Glasses> glassesEntities){
        return glassesEntities.stream().map(this::singleToDTO).toList();
    }

}
