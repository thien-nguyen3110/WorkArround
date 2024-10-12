
package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity that extends JpaRepository.
 * 
 * Enhancements:
 * - Improved method names for better clarity.
 * 
 * Author: Vivek Bengre
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by their ID
    User findById(int id);

    // Delete user by their ID
    void deleteById(int id);
}
