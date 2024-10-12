
package onetomany.Users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entity that extends JpaRepository.
 * 
 * Enhancements:
 * - Improved method names for better clarity.
 * 
 * Author: Vivek Bengre
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by their ID
    User findById(int id);

    // Delete user by their ID
    void deleteById(int id);
}
