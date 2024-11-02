package ro.ubb.mp.controller.dto.response.product;

import ro.ubb.mp.controller.dto.response.product_image.ProductImageResponseDTO;
import ro.ubb.mp.controller.dto.response.product_size.ProductSizeStockResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String categoryName;
    private String brandName;
    List<ProductImageResponseDTO> images;
    List<ProductSizeStockResponseDTO> productStock;
}
