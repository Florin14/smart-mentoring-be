package ro.ubb.mp.controller.dto.response.product_size;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductSizeResponseDTO {
    private Long id;
    private String name;
    private boolean isForShoes;
    private boolean isForKids;
}
