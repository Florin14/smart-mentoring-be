package ro.ubb.mp.controller.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeStockRequestDTO {
    private int stockQuantity;
    private Long sizeId;
}
