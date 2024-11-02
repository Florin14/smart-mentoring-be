package ro.ubb.mp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSizeRequestDTO {
    private String name;
    private boolean isForShoes;
    private boolean isForKids;
}
