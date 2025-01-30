package ro.ubb.mp.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.mp.controller.dto.mapper.UserProfilePictureMapper;
import ro.ubb.mp.controller.dto.mapper.UserProfileResponseMapper;
import ro.ubb.mp.controller.dto.request.ProfileRequestDTO;
import ro.ubb.mp.controller.dto.response.ResponseWrapperDTO;
import ro.ubb.mp.controller.dto.response.UserProfilePictureResponseDTO;
import ro.ubb.mp.controller.dto.response.user.UserProfileDTO;
import ro.ubb.mp.dao.model.User;
import ro.ubb.mp.dao.model.UserProfilePicture;
import ro.ubb.mp.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Getter
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserProfileResponseMapper userMapper;
    private final UserProfilePictureMapper userProfilePictureMapper;


    /**
     * This endpoint will let only users that have the MENTOR authority(ROLE) to access it.
     * In case the MENTOR authority is missing the 403 code is being returned
     * (403 Forbidden => Means we are lacking the necessary authorities(ROLES) to access the given resource
     */
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('MENTOR')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<User>> getMentors() {
        return ResponseEntity.ok().body(userService.getAllMentors());
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getStudents() {
        return ResponseEntity.ok().body(userService.getAllStudents());
    }

    @GetMapping("/users/profile")
    public ResponseEntity<ResponseWrapperDTO<UserProfileDTO>> getProfileInfo(final Authentication authentication) {

        if (authentication.getPrincipal() instanceof User user) {

            User fetchedUser = (User) userService.loadUserByUsername(user.getUsername());

            UserProfileDTO userProfileDTO = getUserMapper().toProfileDTO(fetchedUser);

//            Optional<UserProfilePicture> imageEntity = userService.findUserProfilePicture(user.getId());
//
//            imageEntity.ifPresent(avatar -> userProfileDTO.setAvatar(Base64.getEncoder().encodeToString(profilePicture.getImageData())));

            return ResponseEntity
                    .ok(ResponseWrapperDTO
                            .<UserProfileDTO>builder()
                            .data(userProfileDTO)
                            .build()
                    );
        }

        return ResponseEntity
                .badRequest()
                .body(ResponseWrapperDTO
                        .<UserProfileDTO>builder()
                        .error("Wrong authentication type")
                        .build()
                );
    }

    @PutMapping(
            path = "/users/profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseWrapperDTO<UserProfileDTO>> updateProfile(@ModelAttribute ProfileRequestDTO profileRequestDTO,
                                                                            final Authentication authentication) throws IOException {

        if (authentication.getPrincipal() instanceof User user) {

            profileRequestDTO.setId(user.getId());

            UserProfileDTO userProfileDTO = getUserMapper().toProfileDTO(getUserService().updateProfile(profileRequestDTO));

            if (profileRequestDTO.getProfilePicture() != null) {
                MultipartFile profilePicture = profileRequestDTO.getProfilePicture();
                String fileName = profileRequestDTO.getId() + "_profilePicture.jpg";

                try {
                    byte[] imageData = profilePicture.getBytes();

                    Optional<UserProfilePicture> existingProfilePicture = userService.findUserProfilePicture(user.getId());

                    if (existingProfilePicture.isPresent()) {
                        userService.updateUserProfilePicture(user.getId(), imageData);
                    } else {
                        UserProfilePicture newImageEntity = UserProfilePicture.builder()
                                .imageData(imageData)
                                .imageName(fileName)
                                .user(user)
                                .build();
                        userService.saveUserProfilePicture(newImageEntity);
                    }

//                    userProfileDTO.setAvatar(Base64.getEncoder().encodeToString(imageData));

                } catch (IOException e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(ResponseWrapperDTO
                                    .<UserProfileDTO>builder()
                                    .error("Failed to process the profile picture")
                                    .build()
                            );
                }
            }

            // Return the updated profile with the potentially updated avatar
            return ResponseEntity
                    .ok(ResponseWrapperDTO
                            .<UserProfileDTO>builder()
                            .data(userProfileDTO)
                            .build()
                    );
        }

        return ResponseEntity
                .badRequest()
                .body(ResponseWrapperDTO
                        .<UserProfileDTO>builder()
                        .error("Wrong authentication type")
                        .build()
                );
    }

    @GetMapping("/users/profile/picture")
    public ResponseEntity<?> getProfileImage(final Authentication authentication) {

        if (authentication.getPrincipal() instanceof User user) {
            Optional<UserProfilePicture> profilePicture = userService.findUserProfilePicture(user.getId());

            UserProfilePictureResponseDTO profilePictureDTO = userProfilePictureMapper.toDTO(profilePicture);

            return ResponseEntity.ok(ResponseWrapperDTO
                    .<UserProfilePictureResponseDTO>builder()
                    .data(profilePictureDTO)
                    .build()
            );
        }

        return ResponseEntity
                .badRequest()
                .body(ResponseWrapperDTO
                        .<UserProfilePictureResponseDTO>builder()
                        .error("Wrong authentication type")
                        .build()
                );
    }

    @GetMapping("/users/{userId}/profile/picture")
    public ResponseEntity<?> getProfileImageByUser(@PathVariable final Long userId, final Authentication authentication) throws IOException {
        User user = userService.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Optional<UserProfilePicture> profilePicture = userService.findUserProfilePicture(user.getId());

        if (authentication.getPrincipal() instanceof User) {
            UserProfilePictureResponseDTO dto = userProfilePictureMapper.toDTO(profilePicture);

            ResponseWrapperDTO<UserProfilePictureResponseDTO> response = ResponseWrapperDTO
                    .<UserProfilePictureResponseDTO>builder()
                    .data(dto)
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .badRequest()
                .body(ResponseWrapperDTO
                        .<UserProfilePictureResponseDTO>builder()
                        .error("Wrong authentication type")
                        .build()
                );
    }

    @PreAuthorize("hasAnyAuthority('MENTOR')")
    @GetMapping("/mentors/appointments/students")
    public ResponseEntity<ResponseWrapperDTO<List<UserProfileDTO>>> getAppointmentsUsersByMentor(final Authentication authentication) throws IOException {

        if (authentication.getPrincipal() instanceof User mentor) {

            List<User> appointmentsStudents = getUserService().findAllAnnouncementsUsersByMentor(mentor);

            return ResponseEntity
                    .ok(ResponseWrapperDTO
                            .<List<UserProfileDTO>>builder()
                            .data(appointmentsStudents.stream().map(student -> getUserMapper().toProfileDTO(student)).toList())
                            .build());

        }

        return ResponseEntity
                .badRequest()
                .body(ResponseWrapperDTO
                        .<List<UserProfileDTO>>builder()
                        .error("Wrong authentication type")
                        .build()
                );
    }
}
