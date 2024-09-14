package ro.ubb.mp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.mp.dao.model.UserProfilePicture;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM user_profile_picture WHERE user_id=?1 LIMIT 1")
    Optional<UserProfilePicture> findProfilePictureByUserId(Long userId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE user_profile_picture SET image_data=?2 WHERE user_id=?1")
    void updateProfilePictureByUserId(Long userId, byte[] imageData);
}
