package ro.ubb.mp.controller.dto.response.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
}
