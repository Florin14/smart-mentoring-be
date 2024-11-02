package ro.ubb.mp.controller.dto.response.product_image;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProductImageResponseDTO {
    private Long id;
    private String imageName;
    private byte[] imageData;
}
