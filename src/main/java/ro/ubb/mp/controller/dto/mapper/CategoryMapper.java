package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.category.CategoryResponseDTO;
import ro.ubb.mp.dao.model.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDTO toDTO(Category category);
}
