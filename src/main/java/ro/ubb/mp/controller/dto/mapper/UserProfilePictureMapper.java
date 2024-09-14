package ro.ubb.mp.controller.dto.mapper;

import org.mapstruct.Mapper;
import ro.ubb.mp.controller.dto.response.UserProfilePictureResponseDTO;
import ro.ubb.mp.dao.model.UserProfilePicture;

import java.util.Base64;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserProfilePictureMapper {

    default UserProfilePictureResponseDTO toDTO(Optional<UserProfilePicture> userProfilePictureOpt) {
        if (userProfilePictureOpt.isPresent()) {
            UserProfilePicture userProfilePicture = userProfilePictureOpt.get();
            return UserProfilePictureResponseDTO.builder()
                    .profilePicture(Optional.ofNullable(Base64.getEncoder().encodeToString(userProfilePicture.getImageData())))
                    .build();
        }
        return UserProfilePictureResponseDTO.builder()
                .profilePicture(Optional.empty())
                .build();
    }
}