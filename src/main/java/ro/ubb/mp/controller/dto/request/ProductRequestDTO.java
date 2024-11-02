package ro.ubb.mp.controller.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private Long brand_id;
    private Long category_id;
    private Long gender_id;
    private String sku;
    List<MultipartFile> images;
    String product_stock;
}
