package coms309.repository;

import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserNameAndEmailAndPassword (@Param("user_name") String userName,@Param("email") String email, @Param("password") String password);

    Optional<UserProfile> findByUserNameAndPassword(@Param("user_name") String userName, @Param("password") String password);
}
