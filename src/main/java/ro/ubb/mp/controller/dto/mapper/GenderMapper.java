package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.gender.GenderResponseDTO;
import ro.ubb.mp.dao.model.Gender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenderMapper {
    GenderResponseDTO toDTO(Gender gender);

}
