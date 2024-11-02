package ro.ubb.mp.controller.dto.mapper;

import ro.ubb.mp.controller.dto.response.product_image.ProductImageResponseDTO;
import ro.ubb.mp.dao.model.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    default ProductImageResponseDTO toDTO(ProductImage productImage) {
        if (productImage == null) {
            return null;
        }

        try {
            return ProductImageResponseDTO.builder()
                    .id(productImage.getId())
                    .imageName(productImage.getImageName())
                    .imageData(productImage.getImageData())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

}
