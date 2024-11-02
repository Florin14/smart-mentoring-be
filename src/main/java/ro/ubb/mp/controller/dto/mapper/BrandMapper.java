package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import ro.ubb.mp.dao.model.Brand;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponseDTO toDTO(Brand brand);
}
