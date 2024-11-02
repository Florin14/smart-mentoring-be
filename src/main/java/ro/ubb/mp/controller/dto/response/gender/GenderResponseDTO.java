package ro.ubb.mp.controller.dto.response.gender;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class GenderResponseDTO {
    private Long id;
    private String name;
    private char genderCode;
}
