package ro.ubb.mp.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ro.ubb.mp.controller.dto.request.ProfileRequestDTO;
import ro.ubb.mp.controller.dto.request.UserRequestDTO;
import ro.ubb.mp.dao.model.User;
import ro.ubb.mp.dao.model.UserProfilePicture;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAll();
    List<User> getAllMentors();
    List<User> getAllStudents();

    Optional<User> getUserById(Long id);

    User saveUser(UserRequestDTO userDTO);

    User updateUser(Long id, UserRequestDTO userDTO);

    void deleteUserByEmail(String email);

    void deleteUserById(Long id);

    User updateProfile(ProfileRequestDTO profileRequestDTO);

    List<User> findAllAnnouncementsUsersByMentor(User mentor);

    Optional<UserProfilePicture> findUserProfilePicture(Long userId);

    UserProfilePicture saveUserProfilePicture(UserProfilePicture userProfilePicture);

    void updateUserProfilePicture(Long userId, byte[] imageData);
}
