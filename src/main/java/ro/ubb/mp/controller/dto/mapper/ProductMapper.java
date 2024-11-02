package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.product.ProductResponseDTO;
import ro.ubb.mp.dao.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "brand.name", target = "brandName")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toDTO(Product product);
}
