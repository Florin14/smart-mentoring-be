package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.product_size.ProductSizeResponseDTO;
import ro.ubb.mp.dao.model.ProductSize;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSizeMapper {
    ProductSizeResponseDTO toDTO(ProductSize productSize);

}
