package ro.ubb.mp.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;


@Data
@Builder
public class UserProfilePictureResponseDTO {
    private Optional<String> profilePicture;
}
