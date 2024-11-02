package ro.ubb.mp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequestDTO {
    private Long userId;
}
