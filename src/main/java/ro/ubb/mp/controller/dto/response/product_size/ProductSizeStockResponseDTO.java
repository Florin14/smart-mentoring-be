package ro.ubb.mp.controller.dto.response.product_size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ProductSizeStockResponseDTO {
    private Long id;
    private int stockQuantity;
}
