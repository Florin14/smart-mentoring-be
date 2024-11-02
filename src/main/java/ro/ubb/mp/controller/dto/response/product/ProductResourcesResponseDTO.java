package ro.ubb.mp.controller.dto.response.product;

import ro.ubb.mp.controller.dto.response.brand.BrandResponseDTO;
import ro.ubb.mp.controller.dto.response.category.CategoryResponseDTO;
import ro.ubb.mp.controller.dto.response.gender.GenderResponseDTO;
import ro.ubb.mp.controller.dto.response.product_size.ProductSizeResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResourcesResponseDTO {
    private List<BrandResponseDTO> brands;
    private List<CategoryResponseDTO> categories;
    private List<GenderResponseDTO> genders;
    private List<ProductSizeResponseDTO> productSizes;
}
